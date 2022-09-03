package com.ec.rommel.nttdata.challenge.entity;


import com.ec.rommel.nttdata.challenge.enums.MovementType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movements")
@EqualsAndHashCode(of = "movementId")
@Builder
public class Movement {

    @Id
    @GeneratedValue
    private UUID movementId;

    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    private BigDecimal amount;

    private BigDecimal currentBalance;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    @NotNull
    private BankAccount bankAccount;

}
