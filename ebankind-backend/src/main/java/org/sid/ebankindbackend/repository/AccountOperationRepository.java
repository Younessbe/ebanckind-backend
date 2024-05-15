package org.sid.ebankindbackend.repository;

import org.sid.ebankindbackend.entities.AccountOperation;
import org.sid.ebankindbackend.entities.BankAccount;
import org.sid.ebankindbackend.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,String> {
 List<AccountOperation> findByBankAccountId(String accountId);
Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);
}
