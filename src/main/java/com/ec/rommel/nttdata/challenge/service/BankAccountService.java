package com.ec.rommel.nttdata.challenge.service;

import com.ec.rommel.nttdata.challenge.entity.BankAccount;
import com.ec.rommel.nttdata.challenge.presentation.presenter.BankAccountPresenter;

import java.util.UUID;

public interface BankAccountService {

   BankAccountPresenter getBankAccountByNumber(String bankAccountNumber);

   BankAccountPresenter saveBankAccount(BankAccountPresenter bankAccountPresenter);

   BankAccountPresenter updateBankAccount(BankAccountPresenter bankAccountPresenter);

   void deleteBankAccountById(UUID bankAccountId);

   BankAccountPresenter toPresenter(BankAccount account);
}
