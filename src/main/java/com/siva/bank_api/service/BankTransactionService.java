package com.siva.bank_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.siva.bank_api.model.BankTransactionEntity;
import com.siva.bank_api.repository.BankTransactionRepository;

@Service
public class BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;

    public BankTransactionService(
            BankTransactionRepository bankTransactionRepository) {

        this.bankTransactionRepository = bankTransactionRepository;
    }

    public List<BankTransactionEntity> getTransactions(int accountNumber) {

        return bankTransactionRepository
                .findByAccountNumberOrderByIdDesc(accountNumber);
    }
}