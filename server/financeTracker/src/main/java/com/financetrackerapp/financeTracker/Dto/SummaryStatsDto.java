package com.financetrackerapp.financeTracker.Dto;

import java.util.List;

public class SummaryStatsDto {
    private Double totalIncome;
    private Double totalExpenses;
    private Double netBalance;
    private List<TransactionDto> recentTransactions;

    // Default constructor for JSON deserialization
    public SummaryStatsDto() {
    }

    public SummaryStatsDto(Double totalIncome, Double totalExpenses,
                         Double netBalance, List<TransactionDto> recentTransactions) {
        this.totalIncome = totalIncome != null ? totalIncome : 0.0;
        this.totalExpenses = totalExpenses != null ? totalExpenses : 0.0;
        this.netBalance = netBalance != null ? netBalance : 0.0;
        this.recentTransactions = recentTransactions;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome != null ? totalIncome : 0.0;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses != null ? totalExpenses : 0.0;
    }

    public Double getNetBalance() {
        return netBalance;
    }

    public void setNetBalance(Double netBalance) {
        this.netBalance = netBalance != null ? netBalance : 0.0;
    }

    public List<TransactionDto> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<TransactionDto> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }
}
