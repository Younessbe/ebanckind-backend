package org.sid.ebankindbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankindbackend.entities.BankAccount;

import java.util.List;


@Data
public class CustomerDTO {

    private  Long id;
    private  String nom;
    private String email;



}
