package org.sid.ebankindbackend.repository;

import org.sid.ebankindbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    
}
