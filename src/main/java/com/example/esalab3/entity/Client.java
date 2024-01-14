package com.example.esalab3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idclient")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "contact")
    private String contact;

    @Column(name = "tariff")
    private String tariff;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "idprovider")
    private Provider provider;

    // Constructors, getters, and setters

    // Constructors
    public Client() {
    }

    public Client(String name, String address, String contact, String tariff, Provider provider) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.tariff = tariff;
        this.provider = provider;
    }

    // Getters and Setters
    // Please generate or write your own getters and setters for the fields

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getNameProvider() {
        return this.provider.getName();
    }

    public void setNameProvider(String nameProvider) {
        this.provider.setName(nameProvider);
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Provider getProvider() {
        return this.provider;
    }
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", tariff='" + tariff + '\'' +
                ", provider=" + (provider != null ? provider.getName() : "null") +
                '}';
    }
}
