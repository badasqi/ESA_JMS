package com.example.esalab3.service;

import com.example.esalab3.entity.ChangeLog;
import com.example.esalab3.entity.Provider;
import com.example.esalab3.messaging.Producer;
import com.example.esalab3.repository.ChangeLogRepository;
import com.example.esalab3.repository.ProviderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@EnableTransactionManagement
public class ProviderService {
    private ProviderRepository providerRepository;
    private Producer producer;
    private ChangeLogRepository changeLogRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository, Producer producer, ChangeLogRepository changeLogRepository) {
        this.producer = producer;
        this.providerRepository = providerRepository;
        this.changeLogRepository = changeLogRepository;
    }

    @Transactional
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    @Transactional
    public Provider getProviderById(Integer id) {
        Optional<Provider> providerOptional = providerRepository.findById(id);
        return providerOptional.orElse(null);
    }

    @Transactional
    public void addProvider(Provider provider) {
        providerRepository.save(provider);
        ChangeLog changeLog = new ChangeLog();
        changeLog.setChangeType("create");
        changeLog.setEntityClass("provider");
        changeLog.setChangeTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        changeLog.setChangeDetails("Create provider: " + provider.getName());
        changeLogRepository.save(changeLog);
        producer.produce(changeLog);
    }

    @Transactional
    public void updateProvider(Integer id, Provider providerDetails) {
        Optional<Provider> providerOptional = providerRepository.findById(id);
        Provider provider = providerOptional.get();
        ChangeLog changeLog = new ChangeLog();
        changeLog.setChangeType("update");
        changeLog.setEntityClass("provider");
        changeLog.setChangeTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        changeLog.setChangeDetails("Update name provider: " + provider.getName() + ", new name: " + providerDetails.getName());
        provider.setName(providerDetails.getName());
        changeLogRepository.save(changeLog);
        producer.produce(changeLog);
        providerRepository.save(provider);
    }

    @Transactional
    public void deleteProvider(Integer id) {
        Optional<Provider> providerOptional = providerRepository.findById(id);
        ChangeLog changeLog = new ChangeLog();
        changeLog.setChangeType("delete");
        changeLog.setEntityClass("provider");
        changeLog.setChangeTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        changeLog.setChangeDetails("Delete provider: " + providerOptional.get().getName());
        changeLogRepository.save(changeLog);
        producer.produce(changeLog);
        providerRepository.deleteById(id);
    }
}