package com.financetrackerapp.financeTracker.Repository;

import com.financetrackerapp.financeTracker.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.type = 'INCOME' AND t.users.id = :userId")
    Double sumIncomeByUser(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.type = 'EXPENSE' AND t.users.id = :userId")
    Double sumExpensesByUser(@Param("userId") Long userId);

    @Query("SELECT t FROM Transaction t WHERE t.users.id = :userId ORDER BY t.date DESC LIMIT 5")
    List<Transaction> findRecentTransactionsByUser(@Param("userId") Long userId);
}
