package fr.unice.polytech.si5.al.creditrama.teamd.cardservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@ToString
@EqualsAndHashCode
public class Card implements Serializable {

    @Id
    BigInteger number;

    @NotNull
    String owner;

    @Digits(integer = 3, fraction = 0)
    @NotNull
    Integer cvc;

    @Digits(integer = 4, fraction = 0)
    @NotNull
    Integer code;

    @NotNull
    String iban;
}
