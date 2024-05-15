package org.sid.ebankindbackend.dtos;


import lombok.Data;
import org.sid.ebankindbackend.enums.AccountStatus;
import java.util.Date;


@Data
public  class SavingBankAccountDTO extends BankAccountDTO{

    private String id;
    private double balance;
    private Date createsAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;

}
