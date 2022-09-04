package com.ec.rommel.nttdata.challenge.presentation.controller;


import com.ec.rommel.nttdata.challenge.presentation.presenter.ClientPresenter;
import com.ec.rommel.nttdata.challenge.service.ClientService;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Generated
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/createClient")
    public ClientPresenter saveClient(@RequestBody ClientPresenter clientPresenter) throws IOException {
        return clientService.saveClient(clientPresenter);
    }

    @GetMapping("/getClientById")
    public ClientPresenter getClientById(@RequestParam("clientId") UUID clientId) {
        return clientService.toPresenter(clientService.getClientById(clientId));
    }

    @PutMapping("/updateClient")
    public ClientPresenter updateClient(@RequestBody ClientPresenter clientPresenter) {
        return clientService.updateClient(clientPresenter);
    }

    @DeleteMapping("/deleteClientById")
    public void deleteClientById(@RequestParam("clientId") UUID clientId) {
        clientService.deleteClientById(clientId);
    }

}
