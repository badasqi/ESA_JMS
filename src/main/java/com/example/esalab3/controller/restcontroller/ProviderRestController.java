package com.example.esalab3.controller.restcontroller;

import com.example.esalab3.entity.Provider;
import com.example.esalab3.service.ProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static org.springframework.http.MediaType.TEXT_HTML;


@RestController
@RequestMapping("/api/providers")
public class ProviderRestController {

    private final ProviderService providerService;


    private final XmlMapper xmlMapper;

    @Autowired
    public ProviderRestController(ProviderService providerService, XmlMapper xmlMapper) {
        this.providerService = providerService;
        this.xmlMapper = xmlMapper;
    }

    @GetMapping( produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllProviders(@RequestHeader("Accept") String acceptHeader) {
        List<Provider> providers = providerService.getAllProviders();
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Source xslt = new StreamSource("src/main/resources/xslt/providers.xslt");
                Transformer transformer = transformerFactory.newTransformer(xslt);
                StringWriter stringWriter = new StringWriter();
                transformer.transform(new StreamSource(new ByteArrayInputStream(xmlMapper.writeValueAsBytes(providers))), new StreamResult(stringWriter));
                return ResponseEntity.ok().contentType(TEXT_HTML).body(stringWriter.toString());
            } catch (JsonProcessingException | TransformerException e) {
                e.printStackTrace();
            }
        }
        else if (acceptHeader.equals(MediaType.ALL_VALUE) || acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE))
            return ResponseEntity.ok().body(providers);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProviderById(@PathVariable("id") Integer id) {
        Provider provider = providerService.getProviderById(id);
        try {
            if (provider != null) {
                return ResponseEntity.ok().body(provider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> addProvider(@RequestBody Provider provider) {
        providerService.addProvider(provider);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProvider(@PathVariable("id") Integer id, @RequestBody Provider providerNew) {
        Provider provider = providerService.getProviderById(id);
        if (provider != null) {
            providerService.updateProvider(id, providerNew);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable("id") Integer id) {
        Provider provider = providerService.getProviderById(id);
        if (provider != null) {
            providerService.deleteProvider(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

