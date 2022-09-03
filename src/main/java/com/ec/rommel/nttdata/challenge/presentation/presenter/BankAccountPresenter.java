package com.ec.rommel.nttdata.challenge.presentation.presenter;

import com.ec.rommel.nttdata.challenge.enums.BankAccountStatus;
import com.ec.rommel.nttdata.challenge.enums.BankAccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountPresenter {

    private UUID bankAccountId;
    private String accountNumber;
    private BankAccountType accountType;
    private BigDecimal initialBalance;
    private BankAccountStatus status;
    private UUID clientId;
    private Set<MovementPresenter> movementPresenters;
}
