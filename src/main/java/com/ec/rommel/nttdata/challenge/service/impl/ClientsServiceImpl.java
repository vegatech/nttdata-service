package com.ec.rommel.nttdata.challenge.service.impl;

import com.ec.rommel.nttdata.challenge.entity.BankAccount;
import com.ec.rommel.nttdata.challenge.entity.Client;
import com.ec.rommel.nttdata.challenge.exeption.ValidationException;
import com.ec.rommel.nttdata.challenge.presentation.presenter.BankAccountPresenter;
import com.ec.rommel.nttdata.challenge.presentation.presenter.ClientPresenter;
import com.ec.rommel.nttdata.challenge.repository.ClientRepository;
import com.ec.rommel.nttdata.challenge.service.BankAccountService;
import com.ec.rommel.nttdata.challenge.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ClientsServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BankAccountService bankAccountService;

    @Override
    @Transactional
    public ClientPresenter saveClient(ClientPresenter clientPresenter) throws IOException{
        Optional<Client> optionalClient = clientRepository.findByIdentification(clientPresenter.getIdentification());
        if (optionalClient.isPresent()) {
            throw new ValidationException("Client already exists");
        }
            Client client = new Client();
            client.setName(clientPresenter.getName());
            client.setGender(clientPresenter.getGender());
            client.setAge(clientPresenter.getAge());
            client.setIdentification(clientPresenter.getIdentification());
            client.setAddress(clientPresenter.getAddress());
            client.setPhoneNumber(clientPresenter.getPhoneNumber());
            client.setPassword(clientPresenter.getPassword());
            client.setStatus(clientPresenter.getStatus());
            Client clientSaved = clientRepository.save(client);
            return toPresenter(clientSaved);
    }

    @Override
    public Client getClientById(UUID clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ValidationException("Client was not found"));
        return client;
    }

    @Override
    public ClientPresenter updateClient(ClientPresenter clientPresenter) {
        UUID clientId = clientPresenter.getPersonId();
        if (clientPresenter.getPersonId()== null) {
            throw new ValidationException("Client must have ID");
        }
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            throw new ValidationException("Client not found");
        }
            client.get().setName(clientPresenter.getName());
            client.get().setGender(clientPresenter.getGender());
            client.get().setAge(clientPresenter.getAge());
            client.get().setIdentification(clientPresenter.getIdentification());
            client.get().setAddress(clientPresenter.getAddress());
            client.get().setPhoneNumber(clientPresenter.getPhoneNumber());
            client.get().setPassword(clientPresenter.getPassword());
            client.get().setStatus(clientPresenter.getStatus());
            Client clientSaved = clientRepository.save(client.get());
            return toPresenter(clientSaved);

    }

    @Override
    public void deleteClientById(UUID clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (!client.isPresent()) {
            throw new ValidationException("Client was not found");
        }
            if (client.get().getBankAccounts().size() == 0  && client.get().getBankAccounts().isEmpty()) {
                clientRepository.deleteById(client.get().getPersonId());
            } else {
                throw new ValidationException("The bank account has movement");
            }


    }

    @Override
    public ClientPresenter toPresenter(Client client) {
        Set<BankAccountPresenter> bankAccountPresenters = new HashSet<>();
        if (client.getBankAccounts() != null ) {
            client.getBankAccounts().forEach(bankAccount -> {
                bankAccountPresenters.add(bankAccountService.toPresenter(bankAccount));
            });
        }
        return ClientPresenter.builder()
                .personId(client.getPersonId())
                .name(client.getName())
                .gender(client.getGender())
                .age(client.getAge())
                .address(client.getAddress())
                .identification(client.getIdentification())
                .password(client.getPassword())
                .status(client.isStatus())
                .bankAccountPresenters(bankAccountPresenters)
                .build();
    }
}
