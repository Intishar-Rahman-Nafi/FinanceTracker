package com.financetrackerapp.financeTracker.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.YearMonth;

@Entity
@Table(name = "budgets")
@Getter
@Setter
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private YearMonth month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public Budget() {}

    public Budget(Category category, Double amount, YearMonth month, Users user) {
        this.category = category;
        this.amount = amount;
        this.month = month;
        this.user = user;
    }
}
