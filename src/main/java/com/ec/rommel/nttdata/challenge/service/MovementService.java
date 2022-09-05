package com.ec.rommel.nttdata.challenge.service;

import com.ec.rommel.nttdata.challenge.entity.Movement;
import com.ec.rommel.nttdata.challenge.presentation.presenter.MovementPresenter;
import com.ec.rommel.nttdata.challenge.presentation.presenter.MovementsReportPresenter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface MovementService {
    Movement getMovementById(UUID movementId);

    MovementPresenter saveUpdateMovement(MovementPresenter movementPresenter);

    void deleteMovementById(UUID movementId);


    List<MovementsReportPresenter> getMovementByClientAndDates(UUID clientId, Date initDate, Date endDate);

    MovementPresenter toPresenter(Movement movement);
}
