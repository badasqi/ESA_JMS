package com.example.esalab3.controller;

import com.example.esalab3.entity.Client;
import com.example.esalab3.entity.Provider;
import com.example.esalab3.service.ClientService;
import com.example.esalab3.service.ProviderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    private ProviderService providerService;

    @Autowired
    public void SetProviderService(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/clients")
    public String getAllClients(Model model) {
        List<Client> clients = clientService.getAllClients();
        Provider provider = null;
        for (Client client : clients) {
            provider = client.getProvider();
            // выполнение операций с провайдером
        }
        model.addAttribute("provider", provider);
        model.addAttribute("clients", clients);
        return "clients"; // Имя представления для отображения списка клиентов
    }

    @GetMapping("/clients/new")
    public String showAddProviderForm(Model model) {
        model.addAttribute("client", new Client());
        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);
        return "addClient"; // Возвращает название JSP-файла без расширения
    }

    @PostMapping("/clients")
    public String addProvider(@ModelAttribute("client") Client client, @RequestParam("providerId") Integer providerId) {
        Provider provider = providerService.getProviderById(providerId);
        if (provider != null) {
            client.setProvider(provider);
            clientService.addClient(client);
        }
        return "redirect:/clients";  // Перенаправление на список провайдеров после создания
    }

    @PutMapping("/clients/{id}")
    public String updateClient(@PathVariable("id") Integer id, @RequestParam("name") String name) {
        Client client = clientService.getClientById(id);

        if (client != null) {
            client.setName(name);
            clientService.updateClient(id, client);
        }

        return "redirect:/clients"; // Перенаправление на список клиентов после обновления
    }

    @DeleteMapping("/clients/{id}")
    public String deleteClient(@PathVariable("id") Integer id) {
        clientService.deleteClient(id);
        return "redirect:/clients"; // Перенаправление на список клиентов после удаления
    }
}
