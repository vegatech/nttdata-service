package com.ec.rommel.nttdata.challenge.service.impl;

import com.ec.rommel.nttdata.challenge.entity.Movement;
import com.ec.rommel.nttdata.challenge.presentation.presenter.MovementPresenter;
import com.ec.rommel.nttdata.challenge.service.MovementService;
import org.springframework.stereotype.Service;

@Service
public class MovementServiceImpl implements MovementService {

    @Override
    public MovementPresenter toPresenter(Movement movement) {
        return MovementPresenter.builder()
                .movementId(movement.getMovementId())
                .bankAccountId(movement.getBankAccount().getBankAccountId())
                .transactionDate(movement.getTransactionDate())
                .movementType(movement.getMovementType())
                .amount(movement.getAmount())
                .currentBalance(movement.getCurrentBalance())
                .build();
    }
}
