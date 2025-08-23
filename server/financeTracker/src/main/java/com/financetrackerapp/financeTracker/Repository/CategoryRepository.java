package com.financetrackerapp.financeTracker.Repository;

import com.financetrackerapp.financeTracker.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(Category.CategoryType type);
    List<Category> findByUser_Id(Long userId);
    boolean existsByName(String name);
    boolean existsByNameAndUser_Id(String name, Long userId);

    Optional<Category> findByIdAndUser_Id(Long id, Long userId);
}
