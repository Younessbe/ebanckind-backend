package org.sid.ebankindbackend.services;

import org.sid.ebankindbackend.dtos.*;
import org.sid.ebankindbackend.entities.BankAccount;
import org.sid.ebankindbackend.entities.CurrentAccoount;
import org.sid.ebankindbackend.entities.SavingAccount;
import org.sid.ebankindbackend.exception.BalanceNotSufficentException;
import org.sid.ebankindbackend.exception.BankAccountNotFoundException;
import org.sid.ebankindbackend.exception.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    public CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate , Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount,String description ) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId,double amount,String description ) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination,double anmount) throws BankAccountNotFoundException, BalanceNotSufficentException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
