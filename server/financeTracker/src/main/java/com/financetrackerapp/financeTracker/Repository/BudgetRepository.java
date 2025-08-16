package com.financetrackerapp.financeTracker.Repository;

import com.financetrackerapp.financeTracker.Entity.Budget;
import com.financetrackerapp.financeTracker.Entity.Category;
import com.financetrackerapp.financeTracker.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUser(Users user);
    Optional<Budget> findByUserAndCategoryAndMonth(Users user, Category category, YearMonth month);
    boolean existsByUserAndCategoryAndMonth(Users user, Category category, YearMonth month);
}
