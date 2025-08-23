package com.financetrackerapp.financeTracker.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public enum CategoryType {
        INCOME,
        EXPENSE
    }

    public Category() {}

    public Category(String name, CategoryType type, Users user) {
        this.name = name;
        this.type = type;
        this.user = user;
    }

    public Category(String name, CategoryType type) {
        this.name = name;
        this.type = type;
    }
}
