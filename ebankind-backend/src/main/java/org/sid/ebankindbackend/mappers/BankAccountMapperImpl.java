package org.sid.ebankindbackend.mappers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.sid.ebankindbackend.dtos.AccountOperationDTO;
import org.sid.ebankindbackend.dtos.CurrentBankAccountDTO;
import org.sid.ebankindbackend.dtos.CustomerDTO;
import org.sid.ebankindbackend.dtos.SavingBankAccountDTO;
import org.sid.ebankindbackend.entities.AccountOperation;
import org.sid.ebankindbackend.entities.CurrentAccoount;
import org.sid.ebankindbackend.entities.Customer;
import org.sid.ebankindbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

//MapStruct
@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        //customerDTO.setId(customer.getId());
        //customerDTO.setNom(customer.getNom());
        //customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
       Customer customer =new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){
     SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
     BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
     savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
     savingBankAccountDTO.setType(savingBankAccountDTO.getClass().getSimpleName());
     return savingBankAccountDTO;
    }
    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO){
     SavingAccount savingAccount=new SavingAccount();
     BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
     savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
     return savingAccount;
    }
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccoount currentAccoount){
     CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
     BeanUtils.copyProperties(currentAccoount,currentBankAccountDTO);
     currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccoount.getCustomer()));
     currentBankAccountDTO.setType(currentAccoount.getClass().getSimpleName());
     return currentBankAccountDTO;
    }
    public CurrentAccoount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){
     CurrentAccoount currentAccoount=new CurrentAccoount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccoount);
        currentAccoount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccoount;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }


}
