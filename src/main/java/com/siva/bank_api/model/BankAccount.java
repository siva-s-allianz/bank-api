package com.siva.bank_api.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;
import java.util.List;

public abstract class BankAccount implements Transaction {

    private String customerName;
    private int accountNumber;
    private double balance;

    /** Returns the name of the customer who owns this account. */
    public String getCustomerName(){
        return customerName;
    }

    /** Returns the unique number assigned to this account. */
    public int getAccountNumber(){
        return accountNumber;
    }

    /** Returns the account's current balance. */
    public double getBalance(){
        return balance;
    }
    public String getAccountType() {
    if (this instanceof SavingsAccount) {
        return "Savings Account";
    }

    if (this instanceof CurrentAccount) {
        return "Current Account";
    }

    return "Unknown";
}

    /** Creates a bank account with the supplied customer and opening details. */
    public BankAccount(String customerName, int accountNumber, double balance){
        this.customerName = customerName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    @Override
    /** Adds a positive amount to the account balance. */
    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than 0.");
            return;
        }

        balance = balance + amount;
            BankTransaction transaction =
            new BankTransaction("Deposit", amount);
            transactions.add(transaction);
            transactionStack.push(transaction);
            transactionQueue.offer(transaction);

        System.out.println("Deposited: " + amount);
        System.out.println("New Balance: " + balance);
   }
    
    @Override
    /** Removes a positive amount when sufficient funds are available. */
    public void withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than 0.");
            return;
        }

        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal. Current balance: " + balance);
         
        }

        balance = balance - amount;
        BankTransaction transaction =
        new BankTransaction("Withdraw", amount);
        transactions.add(transaction);
        transactionStack.push(transaction);
        transactionQueue.offer(transaction);
        System.out.println("₹" + amount + " withdrawn successfully.");
        System.out.println("Current Balance: " + balance);
    }
    
    @Override
    /** Prints the account's current balance. */
    public void checkbalance(){
        System.out.println("Current Balance: " + balance);
    }

    /** Prints the concrete type of this account. */
    public abstract void showAccountType();

    public void showTransactionHistory(){
        transactions.display();
    }
    public List<BankTransaction> getTransactionHistory() {
        return transactions.getTransactions();
    }

    private TransactionLinkedList transactions = new TransactionLinkedList();

    private Stack<BankTransaction> transactionStack = new Stack<>();

    private Deque<BankTransaction> transactionQueue = new ArrayDeque<>();

    public void undoLastTransaction() {

    if (transactionStack.isEmpty()) {
        System.out.println("No transaction to undo.");
        return;
    }

    BankTransaction transaction = transactionStack.pop();
    removeFromQueue(transaction);

        if (transaction.getType().equalsIgnoreCase("Deposit")) {

            balance = balance - transaction.getAmount();

        } else if (transaction.getType().equalsIgnoreCase("Withdraw")) {

            balance = balance + transaction.getAmount();
        }

        System.out.println("Last transaction undone.");
        System.out.println("Current Balance: " + balance);
    }
    public void showPendingTransactions() {

        if (transactionQueue.isEmpty()) {
            System.out.println("No pending transactions.");
            return;
        }

        System.out.println("-----Pending Transactions-----");

        for (BankTransaction transaction : transactionQueue) {
            System.out.println(transaction);
        }
    }

    public void processNextTransaction() {

        if (transactionQueue.isEmpty()) {
            System.out.println("No pending transactions.");
            return;
        }

        BankTransaction transaction = transactionQueue.poll();

        System.out.println("Processing transaction:");
        System.out.println(transaction);
    }

    private void removeFromQueue(BankTransaction transaction) {

        transactionQueue.remove(transaction);
    }
}