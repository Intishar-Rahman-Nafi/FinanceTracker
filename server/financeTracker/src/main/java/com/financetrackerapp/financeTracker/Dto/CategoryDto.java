package com.financetrackerapp.financeTracker.Dto;

import com.financetrackerapp.financeTracker.Entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private Category.CategoryType type;
    private Long userId;

    public CategoryDto() {}

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.type = category.getType();
        this.userId = category.getUser() != null ? category.getUser().getId() : null;
    }
}
