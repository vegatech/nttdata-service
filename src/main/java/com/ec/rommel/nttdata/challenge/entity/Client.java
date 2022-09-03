package com.ec.rommel.nttdata.challenge.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends Person {



    private String password;

    private boolean status;

    @OneToMany(mappedBy = "client")
    private Set<BankAccount> bankAccounts;
}
