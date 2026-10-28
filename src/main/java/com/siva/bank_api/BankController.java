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
    public ResponseEntity<String> createAccount(
            @RequestBody BankAccountRequest request) {

        if (request.customerName() == null ||
                request.customerName().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Customer name must not be empty.");
        }

        if (request.accountNumber() <= 0) {
            return ResponseEntity.badRequest()
                    .body("Account number must be greater than 0.");
        }
        
        if (request.accountType() == null ||
                (!request.accountType().equalsIgnoreCase("Savings") &&
                !request.accountType().equalsIgnoreCase("Current"))) {

            return ResponseEntity.badRequest()
                    .body("Account type must be Savings or Current.");
        }

        if (!Double.isFinite(request.balance()) || request.balance() <= 0) {
            return ResponseEntity.badRequest()
                    .body("Initial balance must be greater than zero.");
        }

        String result = bankService.createAccount(
                request.customerName().trim(),
                request.accountNumber(),
                request.balance(),
                request.accountType()
        );

        if (result.equals("Account created successfully")) {
            return ResponseEntity.status(201).body(result);
        }

        return ResponseEntity.badRequest().body(result);
    }


    
    @PostMapping("/accounts/{accountNumber}/deposit")
    public ResponseEntity<String> deposit(
            @PathVariable int accountNumber,
            @RequestBody DepositRequest request) {

        if (accountNumber <= 0) {
            return ResponseEntity.badRequest()
                    .body("Account number must be greater than 0.");
        }

        if (!Double.isFinite(request.amount()) || request.amount() <= 0) {
            return ResponseEntity.badRequest()
                    .body("Deposit amount must be greater than 0.");
        }

        String result = bankService.deposit(
                accountNumber,
                request.amount()
        );

        if ("Deposit successful".equalsIgnoreCase(result)) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
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