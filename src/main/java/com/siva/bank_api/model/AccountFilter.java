package com.siva.bank_api.model;

@FunctionalInterface 
public interface AccountFilter {
    /** Tests whether the supplied bank account matches a condition. */
    boolean test(BankAccount account);
}
