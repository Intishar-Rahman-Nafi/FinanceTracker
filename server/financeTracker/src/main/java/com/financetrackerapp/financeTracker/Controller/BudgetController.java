package com.financetrackerapp.financeTracker.Controller;

import com.financetrackerapp.financeTracker.Dto.BudgetDto;
import com.financetrackerapp.financeTracker.Entity.Budget;
import com.financetrackerapp.financeTracker.Entity.Category;
import com.financetrackerapp.financeTracker.Entity.Users;
import com.financetrackerapp.financeTracker.Repository.BudgetRepository;
import com.financetrackerapp.financeTracker.Repository.CategoryRepository;
import com.financetrackerapp.financeTracker.Utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BudgetController {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final SecurityUtils securityUtils;

    public BudgetController(BudgetRepository budgetRepository, 
                          CategoryRepository categoryRepository,
                          SecurityUtils securityUtils) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/budgets")
    public ResponseEntity<List<BudgetDto>> getBudgets() {
        Users currentUser = securityUtils.getCurrentUser();
        List<Budget> budgets = budgetRepository.findByUser_Id(currentUser.getId());
        List<BudgetDto> budgetDtos = budgets.stream()
                .map(BudgetDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(budgetDtos);
    }

    @PostMapping("/budgets")
    public ResponseEntity<BudgetDto> createBudget(@RequestBody BudgetDto budgetDto) {
        Users currentUser = securityUtils.getCurrentUser();

        // Find the category (ensuring it belongs to the current user)
        Category category = categoryRepository.findById(budgetDto.getCategoryId())
                .filter(cat -> cat.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Category not found for this user"));

        // Check if budget already exists for this user, category, and month
        if (budgetRepository.existsByUserAndCategoryAndMonth(currentUser, category, budgetDto.getMonth())) {
            throw new IllegalStateException("Budget already exists for this category and month");
        }

        // Create and save the new budget
        Budget budget = new Budget();
        budget.setUser(currentUser);
        budget.setCategory(category);
        budget.setAmount(budgetDto.getAmount());
        budget.setMonth(budgetDto.getMonth());

        Budget savedBudget = budgetRepository.save(budget);
        return ResponseEntity.ok(new BudgetDto(savedBudget));
    }
}
