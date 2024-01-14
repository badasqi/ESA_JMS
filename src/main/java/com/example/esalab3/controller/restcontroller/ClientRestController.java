package com.example.esalab3.controller.restcontroller;

import com.example.esalab3.entity.Client;
import com.example.esalab3.entity.Provider;
import com.example.esalab3.service.ClientService;
import com.example.esalab3.service.ProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;

import static org.springframework.http.MediaType.TEXT_HTML;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientRestController {

    private final ClientService clientService;
    private final ProviderService providerService;
    private final XmlMapper xmlMapper;


    @GetMapping
    public ResponseEntity<?> getAllClients(@RequestHeader("Accept") String acceptHeader) {
        List<Client> clients = clientService.getAllClients();
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Source xslt = new StreamSource("src/main/resources/xslt/clients.xslt");
                Transformer transformer = transformerFactory.newTransformer(xslt);
                StringWriter stringWriter = new StringWriter();
                transformer.transform(new StreamSource(new ByteArrayInputStream(xmlMapper.writeValueAsBytes(clients))), new StreamResult(stringWriter));
                return ResponseEntity.ok().contentType(TEXT_HTML).body(stringWriter.toString());
            } catch (JsonProcessingException | TransformerException e) {
                e.printStackTrace();
            }
        }
        else if (acceptHeader.equals(MediaType.ALL_VALUE) || acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE))
            return ResponseEntity.ok().body(clients);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Integer id) {
        Client client = clientService.getClientById(id);
        try {
            if (client != null) {
                return ResponseEntity.ok().body(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> addClient(@RequestBody Client client) {
        Integer providerId = client.getProvider().getId();
        Provider provider = providerService.getProviderById(providerId);

        if (provider != null) {
            client.setProvider(provider);
            clientService.addClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable("id") Integer id, @RequestBody Client clientNew) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            clientService.updateClient(id, clientNew);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Integer id) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            clientService.deleteClient(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
