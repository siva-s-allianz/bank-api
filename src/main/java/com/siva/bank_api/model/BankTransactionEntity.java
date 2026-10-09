package com.siva.bank_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BankTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int accountNumber;
    private String type;
    private double amount;
    private LocalDateTime transactionDate;

    protected BankTransactionEntity() {
    }

    public BankTransactionEntity(
            int accountNumber,
            String type,
            double amount) {

        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}