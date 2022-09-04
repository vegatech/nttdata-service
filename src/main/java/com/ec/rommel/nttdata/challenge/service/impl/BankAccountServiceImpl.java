package com.ec.rommel.nttdata.challenge.service.impl;

import com.ec.rommel.nttdata.challenge.entity.BankAccount;
import com.ec.rommel.nttdata.challenge.entity.Client;
import com.ec.rommel.nttdata.challenge.exeption.ValidationException;
import com.ec.rommel.nttdata.challenge.presentation.presenter.BankAccountPresenter;
import com.ec.rommel.nttdata.challenge.presentation.presenter.MovementPresenter;
import com.ec.rommel.nttdata.challenge.repository.BankAccountRepository;
import com.ec.rommel.nttdata.challenge.repository.ClientRepository;
import com.ec.rommel.nttdata.challenge.service.BankAccountService;
import com.ec.rommel.nttdata.challenge.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MovementService movementService;

    @Override
    public BankAccountPresenter getBankAccountByNumber(String bankAccountNumber) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountNumber(bankAccountNumber);
        if (!bankAccount.isPresent()) {
            throw new ValidationException("Bank account not found");
        }
        return toPresenter(bankAccount.get());
    }

    @Override
    @Transactional
    public BankAccountPresenter saveBankAccount(BankAccountPresenter bankAccountPresenter) {
        String accountNumber = bankAccountPresenter.getAccountNumber();
        if (accountNumber == null || accountNumber.isEmpty() || accountNumber.isBlank()) {
            throw new ValidationException("Bank account must have identification number");
        }
        if (bankAccountPresenter.getClientId() == null) {
            throw new ValidationException("Bank account must have clientId");
        }
        Optional<Client> client = clientRepository.findById(bankAccountPresenter.getClientId());
        if (client.isEmpty()) {
            throw new ValidationException("Client was not found");
        }
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        if (optionalBankAccount.isPresent()) {
            throw new ValidationException("Bank account has already been registered");
        }
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountNumber(bankAccountPresenter.getAccountNumber());
            bankAccount.setAccountType(bankAccountPresenter.getAccountType());
            bankAccount.setInitialBalance(bankAccountPresenter.getInitialBalance());
            bankAccount.setStatus(bankAccountPresenter.getStatus());
            bankAccount.setClient(client.get());
            BankAccount bankAccountSaved = bankAccountRepository.save(bankAccount);
            return toPresenter(bankAccountSaved);

    }

    @Override
    @Transactional
    public BankAccountPresenter updateBankAccount(BankAccountPresenter bankAccountPresenter) {
        UUID bankAccountId = bankAccountPresenter.getBankAccountId();
        if (bankAccountId == null) {
            throw new ValidationException("bankAccountId must have ID");
        }
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(bankAccountId);
        if (bankAccount.isEmpty()) {
            throw new ValidationException("Client not found");
        }
        Optional<Client> client = clientRepository.findById(bankAccountPresenter.getClientId());
        if (client.isEmpty()) {
            throw new ValidationException("Client not found");
        }
        bankAccount.get().setAccountNumber(bankAccountPresenter.getAccountNumber());
        bankAccount.get().setAccountType(bankAccountPresenter.getAccountType());
        bankAccount.get().setInitialBalance(bankAccountPresenter.getInitialBalance());
        bankAccount.get().setStatus(bankAccountPresenter.getStatus());
        bankAccount.get().setClient(client.get());
        BankAccount bankAccountSaved = bankAccountRepository.save(bankAccount.get());
        return toPresenter(bankAccountSaved);
    }

    @Override
    @Transactional
    public void deleteBankAccountById(UUID bankAccountId) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(bankAccountId);
        if (!bankAccount.isPresent()) {
            throw new ValidationException("Bank account not found");
        }
            if (bankAccount.get().getMovements() == null && bankAccount.get().getMovements().isEmpty()) {
                throw new ValidationException("The bank account has movement");
            }
        bankAccountRepository.deleteById(bankAccount.get().getBankAccountId());
    }

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
                .initialBalance(bankAccount.getInitialBalance())
                .movementPresenters(movementPresenters)
                .accountType(bankAccount.getAccountType())
                .status(bankAccount.getStatus())
                .build();
    }
}
