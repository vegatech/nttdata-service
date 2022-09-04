package com.ec.rommel.nttdata.challenge.service;

import com.ec.rommel.nttdata.challenge.entity.Client;
import com.ec.rommel.nttdata.challenge.presentation.presenter.ClientPresenter;

import java.io.IOException;
import java.util.UUID;

public interface ClientService {

    ClientPresenter saveClient(ClientPresenter clientPresenter) throws IOException;

    Client getClientById(UUID clientId);

    ClientPresenter updateClient(ClientPresenter clientPresenter);

    void deleteClientById(UUID clientId);

    ClientPresenter toPresenter(Client client);

}
