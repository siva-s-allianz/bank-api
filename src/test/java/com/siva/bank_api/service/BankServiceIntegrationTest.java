package com.siva.bank_api.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.siva.bank_api.model.BankAccountEntity;
import com.siva.bank_api.model.BankTransactionEntity;
import com.siva.bank_api.repository.BankAccountRepository;

@SpringBootTest
@Transactional
class BankServiceIntegrationTest {

    @Autowired
    private BankService bankService;

    @Autowired
    private BankAccountRepository accountRepository;

    @Test
    void depositShouldPersistBalanceAndTransaction() {

        int accountNumber = 987654;

        BankAccountEntity account = new BankAccountEntity(
                "Integration Test User",
                accountNumber,
                1000.0,
                "Savings Account"
        );

        accountRepository.saveAndFlush(account);

        bankService.deposit(accountNumber, 500.0);

        BankAccountEntity updated =
                accountRepository.findByAccountNumber(accountNumber)
                        .orElseThrow();

        assertEquals(1500.0, updated.getBalance());

        List<BankTransactionEntity> transactions =
                bankService.getTransactionHistory(accountNumber);

        assertEquals(1, transactions.size());
        assertEquals("DEPOSIT", transactions.get(0).getType());
        assertEquals(500.0, transactions.get(0).getAmount());
    }
}