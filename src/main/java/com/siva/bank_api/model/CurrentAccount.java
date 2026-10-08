package com.siva.bank_api.model;

public class CurrentAccount extends BankAccount {
    /** Creates a current account with the supplied customer and opening details. */
    public CurrentAccount(String customerName, int accountNumber, double balance){
        super(customerName, accountNumber, balance);
    }

    @Override
    /** Prints the current account type. */
    public void showAccountType(){
        System.out.println("Account Type : Current Account");
    }
}
