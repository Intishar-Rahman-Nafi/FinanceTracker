package com.financetrackerapp.financeTracker.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.financetrackerapp.financeTracker.Entity.Budget;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
public class BudgetDto {
    private Long id;
    private Long categoryId;
    private Double amount;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
    private YearMonth month;
    private Long userId;

    public BudgetDto() {}

    public BudgetDto(Budget budget) {
        this.id = budget.getId();
        this.categoryId = budget.getCategory().getId();
        this.amount = budget.getAmount();
        this.month = budget.getMonth();
        this.userId = budget.getUser().getId();
    }
}
