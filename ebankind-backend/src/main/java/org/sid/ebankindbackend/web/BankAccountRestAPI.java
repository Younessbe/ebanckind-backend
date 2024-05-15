package org.sid.ebankindbackend.web;

import org.sid.ebankindbackend.dtos.AccountHistoryDTO;
import org.sid.ebankindbackend.dtos.AccountOperationDTO;
import org.sid.ebankindbackend.exception.BankAccountNotFoundException;
import org.sid.ebankindbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;
    public BankAccountRestAPI(BankAccountService bankAccountService){
        this.bankAccountService=bankAccountService;
    }
    @GetMapping("/accounts/{accountsId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
     return bankAccountService.accountHistory(accountId);
    }
    @GetMapping("/accounts/{accountsId}/pageoperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "page",defaultValue ="0" ) int page,
                                               @RequestParam(name = "size",defaultValue = "5")         int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }

}
