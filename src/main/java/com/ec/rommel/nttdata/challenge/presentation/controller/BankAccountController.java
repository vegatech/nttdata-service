package com.ec.rommel.nttdata.challenge.presentation.controller;

import com.ec.rommel.nttdata.challenge.presentation.presenter.BankAccountPresenter;
import com.ec.rommel.nttdata.challenge.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/getBankAccountByNumber")
    public BankAccountPresenter getBankAccountByNumber(@RequestParam("bankAccountNumber") String bankAccountNumber){
        return bankAccountService.getBankAccountByNumber(bankAccountNumber);
    }

    @PostMapping("/createBankAccount")
    BankAccountPresenter saveBankAccount(@RequestBody BankAccountPresenter bankAccountPresenter){
        return bankAccountService.saveBankAccount(bankAccountPresenter);
    }

    @PutMapping("/putBankAccount")
    BankAccountPresenter updateBankAccount(@RequestBody BankAccountPresenter bankAccountPresenter){
        return bankAccountService.updateBankAccount(bankAccountPresenter);
    }

    @DeleteMapping("/deleteBankAccountById")
    public void deleteBankAccountById(@RequestParam("bankAccountId") UUID bankAccountId){
        bankAccountService.deleteBankAccountById(bankAccountId);
    }

}
