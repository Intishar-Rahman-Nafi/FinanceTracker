package com.financetrackerapp.financeTracker.Repository;

import com.financetrackerapp.financeTracker.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(Category.CategoryType type);
    boolean existsByName(String name);
}
