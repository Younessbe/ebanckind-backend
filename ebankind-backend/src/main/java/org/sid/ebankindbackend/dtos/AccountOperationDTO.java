package org.sid.ebankindbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankindbackend.entities.BankAccount;
import org.sid.ebankindbackend.enums.OperationType;

import java.util.Date;


@Data

public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double anmount;
    private OperationType type;
    private String description;


}
