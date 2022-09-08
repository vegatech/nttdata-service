package com.ec.rommel.nttdata.challenge.service.impl;

import com.ec.rommel.nttdata.challenge.entity.BankAccount;
import com.ec.rommel.nttdata.challenge.entity.Client;
import com.ec.rommel.nttdata.challenge.entity.Movement;
import com.ec.rommel.nttdata.challenge.enums.MovementType;
import com.ec.rommel.nttdata.challenge.exeption.ValidationException;
import com.ec.rommel.nttdata.challenge.presentation.presenter.MovementPresenter;
import com.ec.rommel.nttdata.challenge.presentation.presenter.MovementsReportPresenter;
import com.ec.rommel.nttdata.challenge.repository.BankAccountRepository;
import com.ec.rommel.nttdata.challenge.repository.ClientRepository;
import com.ec.rommel.nttdata.challenge.repository.MovementRepository;
import com.ec.rommel.nttdata.challenge.service.MovementService;
import liquibase.repackaged.org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Movement getMovementById(UUID movementId) {
        Optional<Movement> movement = movementRepository.findById(movementId);
        if (movement.isPresent()) {
            throw new ValidationException("Movement not found");
        }
        return movement.get();
    }

    @Override
    public MovementPresenter saveUpdateMovement(MovementPresenter movementPresenter)throws RuntimeException {
        Movement movement;
        UUID movementId = movementPresenter.getMovementId();
        if (movementPresenter.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("The amount of movement must be greater than 0");
        }

        if (movementPresenter.getBankAccountId() == null) {
            throw new ValidationException("Movement must have bankAccountId");
        }

        Optional<BankAccount> bankAccount = bankAccountRepository.findById(movementPresenter.getBankAccountId());
        if (bankAccount.isEmpty()) {
            throw new ValidationException("Bank account not found");
        }
        if (movementId == null) {
            movement = new Movement();
        } else {
            movement = movementRepository.findById(movementPresenter.getMovementId())
                    .orElse(new Movement());
        }
        movement.setMovementType(movementPresenter.getMovementType());
        movement.setTransactionDate(new Date());
        movement.setAmount(movementPresenter.getAmount());
        movement.setBankAccount(bankAccount.get());
        BigDecimal balance = calculateBalance(movement);
        movement.setCurrentBalance(balance);
        Movement movementSaved = movementRepository.save(movement);
        return toPresenter(movementSaved);

    }

    @Override
    public void deleteMovementById(UUID movementId) {
        Optional<Movement> movement = movementRepository.findById(movementId);
        if (!movement.isPresent()) {
            throw new ValidationException("Movement not found");
        }
        movementRepository.deleteById(movement.get().getMovementId());
    }

    @Override
    public List<MovementsReportPresenter> getMovementByClientAndDates(UUID clientId, Date initDate, Date endDate) {
        List<MovementsReportPresenter> movementsReportPresenters = new ArrayList<>();
        if (initDate != null) {
            initDate = com.ec.rommel.nttdata.challenge.utils.DateUtils.instance().asDate(
                    com.ec.rommel.nttdata.challenge.utils.DateUtils.instance().asLocalDateTime(initDate)
                            .withHour(0).withSecond(0).withMinute(0).withNano(0)
            );
        }
        if (endDate != null) {
            endDate = com.ec.rommel.nttdata.challenge.utils.DateUtils.instance().asDate(
                    com.ec.rommel.nttdata.challenge.utils.DateUtils.instance().asLocalDateTime(endDate).withHour(23).withMinute(59).withSecond(59).withNano(0)
            );
        }
        if ((initDate != null && endDate != null) && initDate.compareTo(endDate) > 0) {
            throw new ValidationException("El rango de fechas es inválido");
        }
        Optional<Client> client = clientRepository.findById(clientId);
        if (!client.isPresent()) {
            throw new ValidationException("Client not found");
        }
        List<Object[]> movements = movementRepository.getMovementByClientAndDates(client.get().getPersonId(), initDate, endDate);
        movements.forEach(object ->
                movementsReportPresenters.add(MovementsReportPresenter.builder()
                        .date(object[0].toString())
                        .client((String) object[1])
                        .accountNumber((String) object[2])
                        .accountType((String) object[3])
                        .initialBalance((BigDecimal) object[4])
                        .status((String) object[5])
                        .movementAmount((BigDecimal) object[6])
                        .availableBalance((BigDecimal) object[7])
                        .build())
        );
        return movementsReportPresenters;
    }

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

    private BigDecimal calculateBalance(Movement movement) throws RuntimeException {
        AtomicReference<BigDecimal> balance = new AtomicReference<>(BigDecimal.ZERO);
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(movement.getBankAccount().getBankAccountId());
        if (bankAccount.get().getMovements().isEmpty()) {
            balance.set(bankAccount.get().getInitialBalance());
            if (movement.getMovementType().equals(MovementType.INCOMING)) {
                balance.set(balance.get().add(movement.getAmount()));
            } else if (movement.getMovementType().equals(MovementType.OUTGOING)) {
                balance.set(balance.get().subtract(movement.getAmount()));
            }
        }else {

            BigDecimal in = bankAccount.get().getMovements().stream().filter(m -> m.getMovementType().equals(MovementType.INCOMING)).map(m-> m.getAmount()).reduce(BigDecimal.ZERO,BigDecimal::add);
            BigDecimal out = bankAccount.get().getMovements().stream().filter(m -> m.getMovementType().equals(MovementType.OUTGOING)).map(m-> m.getAmount()).reduce(BigDecimal.ZERO,BigDecimal::add);
            balance.set(bankAccount.get().getInitialBalance().add(in).subtract(out));
            if (movement.getMovementType().equals(MovementType.INCOMING)) {
                balance.set(balance.get().add(movement.getAmount()));
            } else if (movement.getMovementType().equals(MovementType.OUTGOING)) {
                balance.set(balance.get().subtract(movement.getAmount()));
            }
        }
        if (balance.get().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Balance Not Available for this transaction");
        }
        return balance.get();
    }

}
