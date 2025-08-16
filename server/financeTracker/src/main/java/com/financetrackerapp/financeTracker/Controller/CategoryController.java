package com.financetrackerapp.financeTracker.Controller;

import com.financetrackerapp.financeTracker.Dto.CategoryDto;
import com.financetrackerapp.financeTracker.Entity.Category;
import com.financetrackerapp.financeTracker.Repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        System.out.println("Categories: " + categories);
        List<CategoryDto> categoryDtos = categories.stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDtos);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        // Check if category with same name already exists
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new IllegalStateException("Category with name " + categoryDto.getName() + " already exists");
        }
        
        // Create and save new category
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setType(categoryDto.getType());
        
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity
                .created(URI.create("/api/categories/" + savedCategory.getId()))
                .body(new CategoryDto(savedCategory));
    }
}
