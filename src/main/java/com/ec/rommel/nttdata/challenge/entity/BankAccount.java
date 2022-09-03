package com.ec.rommel.nttdata.challenge.entity;


import com.ec.rommel.nttdata.challenge.enums.BankAccountStatus;
import com.ec.rommel.nttdata.challenge.enums.BankAccountType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bankAccounts")
@ToString(of = "bankAccountId")
@EqualsAndHashCode(of = "bankAccountId")
@Builder
@Entity
public class BankAccount {
    @Id
    @GeneratedValue
    private UUID bankAccountId;

    @NotNull
    @Column(unique = true)
    private String accountNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BankAccountType accountType;

    private BigDecimal initialBalance;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BankAccountStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull
    private Client client;

    @OneToMany(mappedBy = "bankAccount")
    private Set<Movement> movements;
}
