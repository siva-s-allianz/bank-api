package com.siva.bank_api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.siva.bank_api.model.BankAccount;
import com.siva.bank_api.service.BankService;
import com.siva.bank_api.model.BankTransaction;
import com.siva.bank_api.model.BankAccountEntity;
import com.siva.bank_api.model.BankTransactionEntity;

@RestController
@RequestMapping("/api")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/accounts")
    public List<BankAccountEntity> getAllAccounts() {
        return bankService.getAllAccounts();
    }

    @GetMapping("/accounts/{accountNumber}")
    public BankAccountEntity getAccountByNumber(
            @PathVariable int accountNumber) {

        return bankService.getAccountByNumber(accountNumber);
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
    public List<BankTransactionEntity> getTransactionHistory(
            @PathVariable int accountNumber) {

        return bankService.getTransactionHistory(accountNumber);
    }

    public record BankAccountRequest(
            String customerName,
            int accountNumber,
            double balance,
            String accountType
    ) {
    }

    @GetMapping("/accounts/{accountNumber}/balance")
public ResponseEntity<Map<String, Object>> getBalance(
        @PathVariable int accountNumber) {

    double balance = bankService.getBalance(accountNumber);

    Map<String, Object> response = new HashMap<>();
    response.put("accountNumber", accountNumber);
    response.put("balance", balance);

    return ResponseEntity.ok(response);
}

}