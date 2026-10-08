package com.siva.bank_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.siva.bank_api.model.BankAccountEntity;


public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
    Optional<BankAccountEntity> findByAccountNumber(int accountNumber);
}
