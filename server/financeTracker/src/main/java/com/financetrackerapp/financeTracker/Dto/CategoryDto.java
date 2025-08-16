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

    public CategoryDto() {}

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.type = category.getType();
    }
}
