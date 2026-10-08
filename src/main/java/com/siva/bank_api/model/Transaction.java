package com.siva.bank_api.model;


public interface Transaction {
    /** Deposits the supplied amount into an account. */
    void deposit(double amount);
    /** Withdraws the supplied amount from an account. */
    void withdraw(double amount);
    /** Displays the current account balance. */
    void checkbalance();
}
