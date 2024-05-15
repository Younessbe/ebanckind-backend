package org.sid.ebankindbackend.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankindbackend.dtos.*;
import org.sid.ebankindbackend.entities.*;
import org.sid.ebankindbackend.enums.OperationType;
import org.sid.ebankindbackend.exception.BalanceNotSufficentException;
import org.sid.ebankindbackend.exception.BankAccountNotFoundException;
import org.sid.ebankindbackend.exception.CustomerNotFoundException;
import org.sid.ebankindbackend.mappers.BankAccountMapperImpl;
import org.sid.ebankindbackend.repository.AccountOperationRepository;
import org.sid.ebankindbackend.repository.BankAccountRepository;
import org.sid.ebankindbackend.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Custommer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer saveCustomer= customerRepository.save(customer);
        return dtoMapper.fromCustomer(saveCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null)
            throw new CustomerNotFoundException("Customer noy found");
        CurrentAccoount currentAccoount= new CurrentAccoount();

        currentAccoount.setId(UUID.randomUUID().toString());
        currentAccoount.setBalance(initialBalance);
        currentAccoount.setCreatesAt(new Date());
        currentAccoount.setBalance(initialBalance);
        currentAccoount.setCustomer(customer);
        currentAccoount.setOverDraft(overDraft);
        CurrentAccoount savedBankAccount= bankAccountRepository.save(currentAccoount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        SavingAccount savingAccount=new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCreatesAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        SavingAccount savedBankAccount= bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savingAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers
                .stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        /* List<CustomerDTO> customerDTOS=new ArrayList<>();
        for(Customer customer:customers){
            CustomerDTO customerDTO=dtoMapper.fromCustomer(customer);
            customerDTOS.add(customerDTO);
        } */
        return customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
       BankAccount bankAccount=bankAccountRepository.findById(accountId)
               .orElseThrow(()->new BankAccountNotFoundException("BankAccount Not Found"));
       if(bankAccount instanceof SavingAccount){
          SavingAccount savingAccount=(SavingAccount) bankAccount;
          return dtoMapper.fromSavingBankAccount(savingAccount);
       }else {
           CurrentAccoount currentAccoount=(CurrentAccoount) bankAccount;
           return dtoMapper.fromCurrentBankAccount(currentAccoount);
       }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount Not Found"));

        if(bankAccount.getBalance()<amount)
         throw new BalanceNotSufficentException("Balance not sufficent");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAnmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount Not Found"));

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAnmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double anmount) throws BankAccountNotFoundException, BalanceNotSufficentException {
     debit(accountIdSource,anmount,"Transfert"+accountIdDestination);
     credit(accountIdDestination,anmount,"Transfert"+accountIdSource);
    }
    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTO = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccoount currentAccoount = (CurrentAccoount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccoount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTO;
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return dtoMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Custommer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer saveCustomer= customerRepository.save(customer);
        return dtoMapper.fromCustomer(saveCustomer);
    }
    @Override
    public void deleCustomer(Long customerId ){
        customerRepository.deleteById(customerId);
    }
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return  accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());


    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount==null)throw new BankAccountNotFoundException("Account Not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

}
