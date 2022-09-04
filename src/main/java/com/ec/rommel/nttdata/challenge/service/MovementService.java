package com.ec.rommel.nttdata.challenge.service;

import com.ec.rommel.nttdata.challenge.entity.Movement;
import com.ec.rommel.nttdata.challenge.presentation.presenter.MovementPresenter;

public interface MovementService {

    MovementPresenter toPresenter(Movement movement);
}
