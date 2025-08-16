package com.financetrackerapp.financeTracker.Service;

import com.financetrackerapp.financeTracker.Dto.SummaryStatsDto;
import com.financetrackerapp.financeTracker.Dto.TransactionDto;
import com.financetrackerapp.financeTracker.Entity.Transaction;
import com.financetrackerapp.financeTracker.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummaryService {
    private final TransactionRepository transactionRepository;

    public SummaryService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public SummaryStatsDto getSummaryStats(Long userId) {
        Double totalIncome = transactionRepository.sumIncomeByUser(userId);
        Double totalExpenses = transactionRepository.sumExpensesByUser(userId);
        Double netBalance = (totalIncome != null ? totalIncome : 0.0) - (totalExpenses != null ? totalExpenses : 0.0);
        
        List<Transaction> recentTransactions = transactionRepository.findRecentTransactionsByUser(userId);
        
        // Convert Transaction entities to TransactionDto objects
        List<TransactionDto> transactionDtos = recentTransactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new SummaryStatsDto(
                totalIncome,
                totalExpenses,
                netBalance,
                transactionDtos
        );
    }
    
    private TransactionDto convertToDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate() != null
                ? transaction.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : null);
        dto.setDescription(transaction.getDescription());
        dto.setCategoryId(transaction.getCategory().getId());
        dto.setCategoryName(transaction.getCategory().getName());
        dto.setType(transaction.getType().name());
        
        return dto;
    }
}
