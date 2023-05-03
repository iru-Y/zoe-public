package com.zoe.controllers;

import com.zoe.models.Contact;
import com.zoe.services.ContactService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Contact>> findAll() {
        List<Contact> contacts = contactService.findAll();
        return ResponseEntity.ok().body(contacts);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Contact> findById(@PathVariable UUID id) {
        Contact contacts = contactService.findById(id);
        return ResponseEntity.ok().body(contacts);
    }

    @PostMapping
    public ResponseEntity<Contact> insert(@RequestBody Contact contact) {
        contact = contactService.insert(contact);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(contact.getId()).toUri();
        return ResponseEntity.created(uri).body(contact);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        contactService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Contact> update(@PathVariable UUID id, @RequestBody Contact contact) {
        contact = contactService.update(id, contact);
        return ResponseEntity.ok().body(contact);
    }
}
