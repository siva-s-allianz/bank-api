package com.siva.bank_api.model;

public class InsufficientBalanceException extends RuntimeException {
    /** Creates an exception describing why a withdrawal could not be completed. */
    public InsufficientBalanceException(String message) {
        super(message);
    }
    
}
