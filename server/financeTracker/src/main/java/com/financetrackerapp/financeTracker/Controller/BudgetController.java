package com.financetrackerapp.financeTracker.Controller;

import com.financetrackerapp.financeTracker.Dto.BudgetDto;
import com.financetrackerapp.financeTracker.Entity.Budget;
import com.financetrackerapp.financeTracker.Entity.Category;
import com.financetrackerapp.financeTracker.Entity.Users;
import com.financetrackerapp.financeTracker.Repository.BudgetRepository;
import com.financetrackerapp.financeTracker.Repository.CategoryRepository;
import com.financetrackerapp.financeTracker.Repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api")
public class BudgetController {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final UsersRepository usersRepository;

    public BudgetController(BudgetRepository budgetRepository, 
                          CategoryRepository categoryRepository,
                          UsersRepository usersRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.usersRepository = usersRepository;
    }

    @PostMapping("/budgets")
    public ResponseEntity<BudgetDto> createBudget(@RequestBody BudgetDto budgetDto) {
        // Find the user
        Users user = usersRepository.findById(budgetDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Find the category
        Category category = categoryRepository.findById(budgetDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Check if budget already exists for this user, category, and month
        if (budgetRepository.existsByUserAndCategoryAndMonth(user, category, budgetDto.getMonth())) {
            throw new IllegalStateException("Budget already exists for this category and month");
        }

        // Create and save the new budget
        Budget budget = new Budget();
        budget.setUser(user);
        budget.setCategory(category);
        budget.setAmount(budgetDto.getAmount());
        budget.setMonth(budgetDto.getMonth());

        Budget savedBudget = budgetRepository.save(budget);
        return ResponseEntity.ok(new BudgetDto(savedBudget));
    }
}
