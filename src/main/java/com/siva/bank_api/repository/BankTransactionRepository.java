package com.siva.bank_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.siva.bank_api.model.BankTransactionEntity;

public interface BankTransactionRepository
        extends JpaRepository<BankTransactionEntity, Long> {

    List<BankTransactionEntity> findByAccountNumberOrderByIdDesc(
            int accountNumber
    );
}