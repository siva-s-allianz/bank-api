package com.siva.bank_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.siva.bank_api.model.BankAccount;
import com.siva.bank_api.repository.BankAccountRepository;
import com.siva.bank_api.model.BankAccountEntity;
import com.siva.bank_api.model.BankTransactionEntity;
import com.siva.bank_api.repository.BankTransactionRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {
    private final List<BankAccount> accounts = new ArrayList<>();
    private final BankAccountRepository bankAccountRepository;
    private final BankTransactionRepository bankTransactionRepository;
    public BankService(BankAccountRepository bankAccountRepository,
                       BankTransactionRepository bankTransactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankTransactionRepository = bankTransactionRepository;
    }

   public String createAccount(
        String customerName,
        int accountNumber,
        double balance,
        String accountType) {

    if (balance <= 0) {
        return "Initial balance must be greater than 0.";
    }

    // Check database for duplicate account number
    if (bankAccountRepository.findByAccountNumber(accountNumber).isPresent()) {
        return "Account already exists.";
    }

    // Validate account type
    if (!accountType.equalsIgnoreCase("savings")
            && !accountType.equalsIgnoreCase("current")) {
        return "Invalid account type.";
    }

    // Create database entity
    BankAccountEntity account = new BankAccountEntity(
            customerName,
            accountNumber,
            balance,
            accountType.equalsIgnoreCase("savings")
                    ? "Savings Account"
                    : "Current Account"
    );

    // Save to H2 database
    bankAccountRepository.save(account);

    return "Account created successfully.";
}
    public List<BankAccountEntity> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccountEntity getAccountByNumber(int accountNumber) {
    return bankAccountRepository
            .findByAccountNumber(accountNumber)
            .orElseThrow(() ->
                    new RuntimeException("Account not found: " + accountNumber)
            );
}

    @Transactional
    public String deposit(int accountNumber, double amount) {

        if (amount <= 0) {
            return "Deposit amount must be greater than 0.";
        }

        BankAccountEntity account = getAccountByNumber(accountNumber);

        account.setBalance(account.getBalance() + amount);
        bankAccountRepository.save(account);

        BankTransactionEntity transaction =
                new BankTransactionEntity(
                        accountNumber,
                        "DEPOSIT",
                        amount
                );

        bankTransactionRepository.save(transaction);

        return "Deposit successful. Current balance: "
                + account.getBalance();
    }

    @Transactional
    public String withdraw(int accountNumber, double amount) {
        if (amount <= 0) {
            return "Withdrawal amount must be greater than 0.";
        }
        BankAccountEntity account = getAccountByNumber(accountNumber);

        if (amount > account.getBalance()) {
            return "Insufficient balance for withdrawal. Current balance: "
                    + account.getBalance();
        }
        account.setBalance(account.getBalance() - amount);
        bankAccountRepository.save(account);
        BankTransactionEntity transaction =
                new BankTransactionEntity(
                        accountNumber,
                        "WITHDRAWAL",
                        amount
                );
        bankTransactionRepository.save(transaction);
        return "Withdrawal successful. Current balance: "
                + account.getBalance();
    }

   public double getBalance(int accountNumber) {
        BankAccountEntity account = getAccountByNumber(accountNumber);
        return account.getBalance();
    }

public BankAccountEntity saveTestAccount() {

    BankAccountEntity account = new BankAccountEntity(
            "Test User",
            9999,
            1000,
            "Savings Account"
    );

    return bankAccountRepository.save(account);
    }

    public List<BankTransactionEntity> getTransactionHistory(int accountNumber) {

    getAccountByNumber(accountNumber);

    return bankTransactionRepository
            .findByAccountNumberOrderByIdDesc(accountNumber);
    }
}
