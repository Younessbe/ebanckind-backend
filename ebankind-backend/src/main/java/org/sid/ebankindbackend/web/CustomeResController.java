package org.sid.ebankindbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankindbackend.dtos.BankAccountDTO;
import org.sid.ebankindbackend.dtos.CustomerDTO;
import org.sid.ebankindbackend.entities.Customer;
import org.sid.ebankindbackend.exception.BankAccountNotFoundException;
import org.sid.ebankindbackend.exception.CustomerNotFoundException;
import org.sid.ebankindbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomeResController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id")Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
      return bankAccountService.saveCustomer(customerDTO);
    }
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){
      customerDTO.setId(customerId);
      return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleCustomer(id);
    }
    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }
}
