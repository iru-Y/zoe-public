package com.zoe.controllers;

import com.zoe.models.Recipients;
import com.zoe.services.RecipientsService;
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
@RequestMapping(value = "/recipients")
public class RecipientsController implements Serializable {
    @Autowired
    private RecipientsService recipientsService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Recipients>> findAll() {
        List<Recipients> recipientsList = recipientsService.findAll();
        return ResponseEntity.ok().body(recipientsList);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Recipients> findById(@PathVariable UUID id) {
        Recipients Recipients = recipientsService.findById(id);
        return ResponseEntity.ok().body(Recipients);
    }

    @PostMapping
    public ResponseEntity<Recipients> insert(@RequestBody Recipients Recipients) {
        Recipients = recipientsService.insert(Recipients);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(Recipients.getId()).toUri();
        return ResponseEntity.created(uri).body(Recipients);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        recipientsService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Recipients> update(@PathVariable UUID id, @RequestBody Recipients Recipients) {
        Recipients = recipientsService.update(id, Recipients);
        return ResponseEntity.ok().body(Recipients);
    }

}
