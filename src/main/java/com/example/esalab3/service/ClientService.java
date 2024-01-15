package com.example.esalab3.service;

import com.example.esalab3.entity.ChangeLog;
import com.example.esalab3.entity.Client;
import com.example.esalab3.messaging.Producer;
import com.example.esalab3.repository.ChangeLogRepository;
import com.example.esalab3.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@EnableTransactionManagement
public class ClientService {

    private final ClientRepository clientRepository;
    private final Producer producer;
    private final ChangeLogRepository changeLogRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, Producer producer, ChangeLogRepository changeLogRepository) {
        this.clientRepository = clientRepository;
        this.producer = producer;
        this.changeLogRepository = changeLogRepository;
    }

    @Transactional
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    @Transactional
    public Client getClientById(Integer id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        return clientOptional.orElse(null);
    }
    @Transactional
    public Client addClient(Client client) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setChangeType("create");
        changeLog.setEntityClass("client");
        changeLog.setChangeTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        changeLog.setChangeDetails("Create client: " + client.toString());
        changeLogRepository.save(changeLog);
        producer.produce(changeLog);
        return clientRepository.save(client);
    }

    @Transactional
    public void updateClient(Integer id, Client clientDetails) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            ChangeLog changeLog = new ChangeLog();
            changeLog.setChangeType("create");
            changeLog.setEntityClass("client");
            changeLog.setChangeTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            changeLog.setChangeDetails("Update client: " + client.toString() + ", new Client: " + clientDetails.toString());
            client.setName(clientDetails.getName());
            client.setAddress(clientDetails.getAddress());
            client.setContact(clientDetails.getContact());
            client.setTariff(clientDetails.getTariff());
            client.setProvider(clientDetails.getProvider());
            changeLogRepository.save(changeLog);
            producer.produce(changeLog);
        }
    }
    @Transactional
    public void deleteClient(Integer id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        ChangeLog changeLog = new ChangeLog();
        changeLog.setChangeType("delete");
        changeLog.setEntityClass("client");
        changeLog.setChangeTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        changeLog.setChangeDetails("Delete client: " + clientOptional.get().toString());
        changeLogRepository.save(changeLog);
        producer.produce(changeLog);
        clientRepository.deleteById(id);
    }
}

