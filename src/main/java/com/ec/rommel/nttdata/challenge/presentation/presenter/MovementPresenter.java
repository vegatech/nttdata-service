package com.ec.rommel.nttdata.challenge.presentation.presenter;

import com.ec.rommel.nttdata.challenge.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementPresenter {
    private UUID movementId;
    private MovementType movementType;
    private Date transactionDate;
    private BigDecimal amount;
    private BigDecimal currentBalance;
    private UUID bankAccountId;
}
