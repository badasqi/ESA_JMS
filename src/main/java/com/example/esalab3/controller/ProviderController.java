package com.example.esalab3.controller;

import com.example.esalab3.entity.Provider;
import com.example.esalab3.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ProviderController {

    private ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }



    @GetMapping("/providers")
    public String getAllProviders(Model model) {
        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);
        return "providers"; // Имя представления для отображения списка провайдеров
    }

    @GetMapping("/providers/new")
    public String showAddProviderForm(Model model) {
        model.addAttribute("provider", new Provider());
        return "addProvider"; // Возвращает название JSP-файла без расширения
    }

    @PostMapping("/providers")
    public String addProvider(@ModelAttribute("provider") Provider provider) {
        providerService.addProvider(provider);
        return "redirect:/providers";  // Перенаправление на список провайдеров после создания
    }

    // Обновление информации о провайдере (PUT)
    @PutMapping("/providers/{id}")
    public String updateProvider(@PathVariable("id") Integer id, Provider providerDetails) {
        Provider provider = providerService.getProviderById(id);

        if (provider != null) {
            provider.setName(providerDetails.getName());
            providerService.updateProvider(id, provider);
        }

        return "redirect:/providers"; // Перенаправление на список провайдеров после обновления
    }

    // Удаление провайдера (DELETE)
    @DeleteMapping("/providers/{id}")
    public String deleteProvider(@PathVariable("id") Integer id) {
        Provider provider = providerService.getProviderById(id);

        if (provider != null) {
            providerService.deleteProvider(id);
        }
        return "redirect:/providers"; // Перенаправление на список провайдеров после удаления
    }
}
