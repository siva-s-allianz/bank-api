package com.siva.bank_api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.siva.bank_api.model.BankAccount;
import com.siva.bank_api.service.BankService;
import com.siva.bank_api.model.BankTransaction;

@RestController
@RequestMapping("/api")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/accounts")
    public List<BankAccount> getAllAccounts() {
        return bankService.getAllAccounts();
    }

    @GetMapping ("/accounts/{accountNumber}")
    public Object getAccountByNumber(@PathVariable int accountNumber) {
        BankAccount account = bankService.getAccountByNumber(accountNumber);
        if (account == null){
            return "Account not found.";
        }
        return account;
    }

    @PostMapping("/accounts")
    public String createAccount(@RequestBody BankAccountRequest request) {

        return bankService.createAccount(
                request.customerName(),
                request.accountNumber(),
                request.balance(),
                request.accountType()
        );
    }

    @PostMapping("/accounts/{accountNumber}/deposit")
    public String deposit(
            @PathVariable int accountNumber,
            @RequestBody DepositRequest request) {

        return bankService.deposit(
                accountNumber,
                request.amount()
        );
    }
    public record DepositRequest(double amount) {
    }


    @PostMapping ("/accounts/{accountNumber}/withdraw")
    public String withdraw(
            @PathVariable int accountNumber,
            @RequestBody WithdrawalRequest request) {

        return bankService.withdraw(accountNumber, request.amount());
    }
    public record WithdrawalRequest(double amount) {
    }

    @GetMapping("/accounts/{accountNumber}/transactions")
    public Object getTransactionHistory(
            @PathVariable int accountNumber) {

        List<BankTransaction> transactions =
                bankService.getTransactionHistory(accountNumber);

        if (transactions == null) {
            return "Account not found.";
        }

        return transactions;
    }

    public record BankAccountRequest(
            String customerName,
            int accountNumber,
            double balance,
            String accountType
    ) {
    }

}