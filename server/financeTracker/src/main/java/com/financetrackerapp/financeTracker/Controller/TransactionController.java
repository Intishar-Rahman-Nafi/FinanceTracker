package com.financetrackerapp.financeTracker.Controller;

import com.financetrackerapp.financeTracker.Dto.TransactionDto;
import com.financetrackerapp.financeTracker.Entity.Category;
import com.financetrackerapp.financeTracker.Entity.Transaction;
import com.financetrackerapp.financeTracker.Entity.Users;
import com.financetrackerapp.financeTracker.Repository.CategoryRepository;
import com.financetrackerapp.financeTracker.Repository.TransactionRepository;
import com.financetrackerapp.financeTracker.Utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final SecurityUtils securityUtils;

    public TransactionController(TransactionRepository transactionRepository,
                              CategoryRepository categoryRepository,
                              SecurityUtils securityUtils) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactions() {
        Users currentUser = securityUtils.getCurrentUser();
        List<Transaction> transactions = transactionRepository.findByUsers_Id(currentUser.getId());
        List<TransactionDto> transactionDtos = transactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactionDtos);
    }

    @PostMapping
    public ResponseEntity<TransactionDto> addTransaction(@RequestBody TransactionDto transactionDto) {
        Users currentUser = securityUtils.getCurrentUser();
                
        // Find and validate category
        Category category = categoryRepository.findByIdAndUser_Id(transactionDto.getCategoryId(), currentUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found for this user"));

        // Create and save transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate() != null 
                ? Date.from(transactionDto.getDate().atZone(ZoneId.systemDefault()).toInstant())
                : new Date());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setCategory(category);
        transaction.setType(Transaction.TransactionType.valueOf(transactionDto.getType()));
        transaction.setUser(currentUser);
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        return ResponseEntity.ok(convertToDto(savedTransaction));
    }
    
    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate() != null 
                ? transaction.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() 
                : null);
        dto.setDescription(transaction.getDescription());
        if (transaction.getCategory() != null) {
            dto.setCategoryId(transaction.getCategory().getId());
            dto.setCategoryName(transaction.getCategory().getName());
        }
        dto.setType(transaction.getType().name());
        return dto;
    }
}
