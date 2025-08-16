package com.financetrackerapp.financeTracker.Dto;

import java.time.LocalDateTime;

public class TransactionDto {
    private Long id;
    private Double amount;
    private LocalDateTime date;
    private String description;
    private Long categoryId;
    private String categoryName;
    private String type;

    public TransactionDto() {
    }

    public TransactionDto(Long id, Double amount, LocalDateTime date, String description, Long categoryId, String categoryName, String type) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.type = type;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
