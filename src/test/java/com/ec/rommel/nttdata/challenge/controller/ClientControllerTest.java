package com.ec.rommel.nttdata.challenge.controller;

import com.ec.rommel.nttdata.challenge.presentation.controller.ClientController;
import com.ec.rommel.nttdata.challenge.presentation.presenter.ClientPresenter;
import com.ec.rommel.nttdata.challenge.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {


    @Mock
    private ClientService clientService;
    @InjectMocks
    private ClientController clientController;

    @Test
    public void shouldGetClientById() {
        UUID clientId = UUID.randomUUID();
        clientController.getClientById(clientId);
        verify(clientService, times(1)).getClientById(clientId);
    }

    @Test
    public void shouldSaveClient()  {
        ClientPresenter clientPresenter = new ClientPresenter();
        clientController.saveClient(clientPresenter);
        verify(clientService, times(1)).saveClient(clientPresenter);
    }

}
