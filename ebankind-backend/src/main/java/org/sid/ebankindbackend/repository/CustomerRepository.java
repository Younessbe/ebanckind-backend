package org.sid.ebankindbackend.repository;

import org.sid.ebankindbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
