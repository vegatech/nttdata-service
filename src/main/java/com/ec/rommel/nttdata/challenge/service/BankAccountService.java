package com.ec.rommel.nttdata.challenge.service;

import com.ec.rommel.nttdata.challenge.entity.BankAccount;
import com.ec.rommel.nttdata.challenge.presentation.presenter.BankAccountPresenter;

public interface BankAccountService {



   BankAccountPresenter toPresenter(BankAccount account);
}
