package org.sid.ebankindbackend;

import jakarta.transaction.Transactional;
import org.sid.ebankindbackend.dtos.BankAccountDTO;
import org.sid.ebankindbackend.dtos.CurrentBankAccountDTO;
import org.sid.ebankindbackend.dtos.CustomerDTO;
import org.sid.ebankindbackend.dtos.SavingBankAccountDTO;
import org.sid.ebankindbackend.entities.*;
import org.sid.ebankindbackend.enums.AccountStatus;
import org.sid.ebankindbackend.enums.OperationType;
import org.sid.ebankindbackend.exception.BalanceNotSufficentException;
import org.sid.ebankindbackend.exception.BankAccountNotFoundException;
import org.sid.ebankindbackend.exception.CustomerNotFoundException;
import org.sid.ebankindbackend.repository.AccountOperationRepository;
import org.sid.ebankindbackend.repository.BankAccountRepository;
import org.sid.ebankindbackend.repository.CustomerRepository;
import org.sid.ebankindbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankindBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(EbankindBackendApplication.class, args);
    }
    @Bean

    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args ->{
             Stream.of("Hassan","Imane","Mohamed").forEach(name->{
                 CustomerDTO customer= new CustomerDTO();
                 customer.setNom(name);
                 customer.setEmail(name+"@gmail.com");
                 bankAccountService.saveCustomer(customer);
             });
             bankAccountService.listCustomers().forEach(customer -> {
                 try {
                     bankAccountService.saveCurrentBankAccount(Math.random()*9000,9000,customer.getId());
                     bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5, customer.getId());

                 } catch (CustomerNotFoundException e) {
                     e.printStackTrace();
                 }

             });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for(BankAccountDTO bankAccount:bankAccounts){
                for (int i=0;i<10;i++){
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId=((SavingBankAccountDTO) bankAccount).getId();
                    }else {
                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId, 10000+Math.random()*12000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                }

            }
        } ;
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){

        return args ->{
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                Customer customer=new Customer();
                customer.setNom(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CurrentAccoount currentAccoount=new CurrentAccoount();

                currentAccoount.setId(UUID.randomUUID().toString());
                currentAccoount.setBalance(Math.random()*90000);
                currentAccoount.setCreatesAt(new Date());
                currentAccoount.setStatus(AccountStatus.CREATED);
                currentAccoount.setCustomer(cust);
                currentAccoount.setOverDraft(9000);
                bankAccountRepository.save(currentAccoount);


                SavingAccount savingAccount =new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatesAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i=0;i<5;i++){
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAnmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }

            });

        };
    }
}
