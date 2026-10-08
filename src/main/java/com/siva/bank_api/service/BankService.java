package com.siva.bank_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.siva.bank_api.model.BankTransaction;
import com.siva.bank_api.model.BankAccount;
import com.siva.bank_api.model.CurrentAccount;
import com.siva.bank_api.model.SavingsAccount;
import com.siva.bank_api.repository.BankAccountRepository;
import com.siva.bank_api.model.BankAccountEntity;

@Service
public class BankService {
    private final List<BankAccount> accounts = new ArrayList<>();
    private final BankAccountRepository bankAccountRepository;
    public BankService(BankAccountRepository bankAccountRepository) {
    this.bankAccountRepository = bankAccountRepository;
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
    public List<BankAccount> getAllAccounts() {
        return accounts;
    }

    public BankAccount getAccountByNumber(int accountNumber){
        for(BankAccount account : accounts){
            if(account.getAccountNumber() == accountNumber){
                return account;
            }
        }
        return null;
    }

    public String deposit(int accountNUmber, double amount){
        BankAccount account = getAccountByNumber(accountNUmber);
        if(account == null){
            return "Account not found.";
        }

        if(amount<=0){
            return "Deposit amount must be greater than 0.";
        }

        account.deposit(amount);
        return "Deposit successful. Current balance:" + account.getBalance();
    }
    public String withdraw(int accountNumber, double amount){
        BankAccount account = getAccountByNumber(accountNumber);

        if(account == null){
            return "Account not found";
        }

        if(amount <= 0){
            return "Withdrawal amount must be greater than 0.";
        }
        try{
            account.withdraw(amount);
            return "Withdrawal successful. Current balance: " + account.getBalance();
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }

   public List<BankTransaction> getTransactionHistory(int accountNumber) {

    BankAccount account = getAccountByNumber(accountNumber);

    if (account == null) {
        return null;
    }

    return account.getTransactionHistory();
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
}
