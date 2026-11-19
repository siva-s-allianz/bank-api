package com.siva.bank_api.dto;

import jakarta.validation.constraints.*;

public class BankAccountForm {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotNull(message = "Account number is required")
    private Long accountNumber;

    @NotNull(message = "Balance is required")
    @PositiveOrZero (message = "Balance cannot be negative")
    private Double balance;

    public String getCustomerName(){
        return customerName;
    }

    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    public Long getAccountNumber(){
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber){
        this.accountNumber = accountNumber;
    }

    public Double getBalance(){
        return balance;
    }

    public void setBalance(Double balance){
        this.balance=balance;
    }
    
}
