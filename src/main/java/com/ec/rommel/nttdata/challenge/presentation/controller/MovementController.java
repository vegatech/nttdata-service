package com.ec.rommel.nttdata.challenge.presentation.controller;

import com.ec.rommel.nttdata.challenge.presentation.presenter.MovementPresenter;
import com.ec.rommel.nttdata.challenge.presentation.presenter.MovementsReportPresenter;
import com.ec.rommel.nttdata.challenge.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class MovementController {


    @Autowired
    private MovementService movementService;

    @GetMapping("/getMovementById")
    public MovementPresenter getMovementById(@RequestParam("movementId") UUID movementId) {
        return movementService.toPresenter(movementService.getMovementById(movementId));
    }

    @PostMapping("/saveUpdateMovement")
    public MovementPresenter saveUpdateMovement(@RequestBody MovementPresenter movementPresenter) {
        return movementService.saveUpdateMovement(movementPresenter);
    }

    @DeleteMapping("/deleteMovement")
    public void deleteMovementById(@RequestParam("movementId") UUID movementId) {
        movementService.deleteMovementById(movementId);
    }

    @GetMapping("/getMovementByClientAndDates")
    public List<MovementsReportPresenter> getMovementByClientAndDates(@RequestParam("clientId") UUID clientId,
                                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date initDate,
                                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        return movementService.getMovementByClientAndDates(clientId,initDate,endDate);
    }
}
