package com.siva.bank_api.model;

public class SavingsAccount extends BankAccount{
    /** Creates a savings account with the supplied customer and opening details. */
    public SavingsAccount(String customerName, int accountNumber, double balance){
        super(customerName, accountNumber, balance);
    }
    @Override
    /** Prints the savings account type. */
    public void showAccountType(){
        System.out.println("Account Type : Savings Account");
    }
    
}
