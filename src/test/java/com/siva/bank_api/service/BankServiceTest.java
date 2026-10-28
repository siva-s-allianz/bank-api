package com.siva.bank_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.siva.bank_api.repository.BankAccountRepository;
import com.siva.bank_api.repository.BankTransactionRepository;
import java.util.Optional;
import com.siva.bank_api.model.BankAccountEntity;
import com.siva.bank_api.model.BankTransactionEntity;

class BankServiceTest {

    private BankService bankService;
    private BankAccountRepository accountRepository;
    private BankTransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        accountRepository = mock(BankAccountRepository.class);
        transactionRepository = mock(BankTransactionRepository.class);

        bankService = new BankService(
                accountRepository,
                transactionRepository
        );
    }

    @Test
    void depositShouldRejectNegativeAmount() {

        String result = bankService.deposit(1001, -500);

        assertEquals(
                "Deposit amount must be greater than 0.",
                result
        );
    }

    @Test
    void withdrawShouldRejectNegativeAmount() {

        String result = bankService.withdraw(1001, -200);

        assertEquals(
                "Withdrawal amount must be greater than 0.",
                result
        );
    }

    @Test
    void depositShouldUpdateBalanceAndSaveTransaction() {

        BankAccountEntity account = new BankAccountEntity(
                "Siva", 1001, 1000.0, "Savings Account"
        );

        when(accountRepository.findByAccountNumber(1001))
                .thenReturn(Optional.of(account));

        String result = bankService.deposit(1001, 500.0);

        assertEquals(1500.0, account.getBalance());

        assertEquals(
                "Deposit successful. Current balance: 1500.0",
                result
        );

        verify(accountRepository).save(account);

        verify(transactionRepository)
                .save(any(BankTransactionEntity.class));
    }

    @Test
void withdrawShouldRejectInsufficientBalance() {

    BankAccountEntity account = new BankAccountEntity(
            "Siva", 1001, 1000.0, "Savings Account"
    );

    when(accountRepository.findByAccountNumber(1001))
            .thenReturn(Optional.of(account));

    String result = bankService.withdraw(1001, 2000.0);

    assertEquals(
            "Insufficient balance for withdrawal. Current balance: 1000.0",
            result
    );

    assertEquals(1000.0, account.getBalance());

    verify(accountRepository, never())
            .save(any(BankAccountEntity.class));

    verify(transactionRepository, never())
            .save(any(BankTransactionEntity.class));
}
}