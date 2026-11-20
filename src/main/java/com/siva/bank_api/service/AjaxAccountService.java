
package com.siva.bank_api.service;

import org.springframework.stereotype.Service;

import com.siva.bank_api.dto.AjaxAccountForm;
import com.siva.bank_api.model.BankAccountEntity;
import com.siva.bank_api.repository.BankAccountRepository;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class AjaxAccountService {

    private final BankAccountRepository bankAccountRepository;

    // Constructor Injection
    public AjaxAccountService(
            BankAccountRepository bankAccountRepository) {

        this.bankAccountRepository = bankAccountRepository;
    }

    public String createAccount(AjaxAccountForm form) {

        int accountNumber = Math.toIntExact(form.getAccountNumber());

        // Check whether the account number already exists
        if (bankAccountRepository.findByAccountNumber(accountNumber).isPresent()) {
            return "Account number already exists!";
        }

        // Create a new account
        BankAccountEntity account = new BankAccountEntity(
                form.getCustomerName(),
                accountNumber,
                form.getBalance(),
                "Savings Account"
        );

        // Save the account
        BankAccountEntity savedAccount;

        try {
            savedAccount = bankAccountRepository.save(account);
        } catch (DataIntegrityViolationException exception) {
            return "Account number already exists! Please use a different number.";
        }

        return "Account created successfully! "
                + "Customer: " + savedAccount.getCustomerName()
                + ", Account Number: " + savedAccount.getAccountNumber()
                + ", Balance: " + savedAccount.getBalance()
                + ", Database ID: " + savedAccount.getId();
    }
}
