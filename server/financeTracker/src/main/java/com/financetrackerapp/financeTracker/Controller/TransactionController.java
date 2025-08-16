package com.financetrackerapp.financeTracker.Controller;

import com.financetrackerapp.financeTracker.Dto.TransactionDto;
import com.financetrackerapp.financeTracker.Entity.Transaction;
import com.financetrackerapp.financeTracker.Entity.Users;
import com.financetrackerapp.financeTracker.Repository.CategoryRepository;
import com.financetrackerapp.financeTracker.Repository.TransactionRepository;
import com.financetrackerapp.financeTracker.Repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final UsersRepository usersRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionController(TransactionRepository transactionRepository, 
                              UsersRepository usersRepository,
                              CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.usersRepository = usersRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDto> transactionDtos = transactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactionDtos);
    }

    @PostMapping
    public ResponseEntity<TransactionDto> addTransaction(@RequestBody TransactionDto transactionDto,
                                                       @RequestParam Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
                
        // Find and validate category
        com.financetrackerapp.financeTracker.Entity.Category category = categoryRepository.findById(transactionDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Create and save transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate() != null 
                ? Date.from(transactionDto.getDate().atZone(ZoneId.systemDefault()).toInstant())
                : new Date());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setCategory(category);
        transaction.setType(Transaction.TransactionType.valueOf(transactionDto.getType()));
        transaction.setUser(user);
        
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
