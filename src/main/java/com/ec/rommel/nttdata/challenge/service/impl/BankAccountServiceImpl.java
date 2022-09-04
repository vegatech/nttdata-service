package com.ec.rommel.nttdata.challenge.service.impl;

import com.ec.rommel.nttdata.challenge.entity.BankAccount;
import com.ec.rommel.nttdata.challenge.presentation.presenter.BankAccountPresenter;
import com.ec.rommel.nttdata.challenge.presentation.presenter.MovementPresenter;
import com.ec.rommel.nttdata.challenge.service.BankAccountService;
import com.ec.rommel.nttdata.challenge.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private MovementService movementService;

    @Override
    public BankAccountPresenter toPresenter(BankAccount bankAccount) {
        Set<MovementPresenter> movementPresenters = new HashSet<>();
        if (bankAccount.getMovements() != null) {
            bankAccount.getMovements().forEach(movement -> {
                movementPresenters.add(movementService.toPresenter(movement));
            });
        }
        return BankAccountPresenter.builder()
                .bankAccountId(bankAccount.getBankAccountId())
                .accountNumber(bankAccount.getAccountNumber())
               // .clientId(bankAccount.getClient().getClientId())
                .initialBalance(bankAccount.getInitialBalance())
                .movementPresenters(movementPresenters)
                .accountType(bankAccount.getAccountType())
                .status(bankAccount.getStatus())
                .build();
    }
}
