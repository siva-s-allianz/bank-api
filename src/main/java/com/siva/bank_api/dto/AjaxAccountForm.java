package com.siva.bank_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Positive;

public class AjaxAccountForm {

    @NotBlank(message = "Customer name is required")
    @jakarta.validation.constraints.Size(
        min = 2,
        max = 50,
        message = "Customer name must contain 2 to 50 characters"
    )
    private String customerName;

    @NotNull(message = "Account number is required")
    @Positive(message = "Account number must be positive")
    private Long accountNumber;

    @NotNull(message = "Balance is required")
    @PositiveOrZero(message = "Balance cannot be negative")
    private Double balance;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}