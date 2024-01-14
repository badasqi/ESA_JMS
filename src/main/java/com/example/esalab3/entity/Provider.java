package com.example.esalab3.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//@Data
@Entity
@Table(name = "provider")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprovider")
    private Integer id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Client> clients = new ArrayList<>();

    public Provider (){}

    public Provider(String name, List<Client> clients){
        this.name = name; this.clients = clients;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    // Геттер и сеттер для списка клиентов
    public List<Client> getClients() {
        return this.clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }


    // Методы для добавления и удаления клиентов
    public void addClient(Client client) {
        clients.add(client);
        client.setNameProvider(this.name);
    }

    public void removeClient(Client client) {
        clients.remove(client);
        client.setNameProvider(null);
    }

}
