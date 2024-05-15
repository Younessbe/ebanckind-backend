package org.sid.ebankindbackend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("CA")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CurrentAccoount extends BankAccount {
    private double overDraft;
}
