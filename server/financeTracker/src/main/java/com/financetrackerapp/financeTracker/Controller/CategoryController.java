package com.financetrackerapp.financeTracker.Controller;

import com.financetrackerapp.financeTracker.Dto.CategoryDto;
import com.financetrackerapp.financeTracker.Entity.Category;
import com.financetrackerapp.financeTracker.Entity.Users;
import com.financetrackerapp.financeTracker.Repository.CategoryRepository;
import com.financetrackerapp.financeTracker.Repository.UsersRepository;
import com.financetrackerapp.financeTracker.Utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final SecurityUtils securityUtils;

    public CategoryController(CategoryRepository categoryRepository, 
                            SecurityUtils securityUtils) {
        this.categoryRepository = categoryRepository;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getCategoriesByUser() {
        Users currentUser = securityUtils.getCurrentUser();
        List<Category> categories = categoryRepository.findByUser_Id(currentUser.getId());
        List<CategoryDto> categoryDtos = categories.stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDtos);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(
            @RequestBody CategoryDto categoryDto) {
        
        Users currentUser = securityUtils.getCurrentUser();
        
        // Check if category with same name already exists for this user
        if (categoryRepository.existsByNameAndUser_Id(categoryDto.getName(), currentUser.getId())) {
            throw new IllegalStateException("Category with name " + categoryDto.getName() + " already exists for this user");
        }
        
        // Create and save new category
        Category category = new Category(categoryDto.getName(), categoryDto.getType(), currentUser);
        
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity
                .created(URI.create("/api/categories/" + savedCategory.getId()))
                .body(new CategoryDto(savedCategory));
    }
}
