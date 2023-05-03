package com.zoe.controllers;

import com.zoe.models.Senders;
import com.zoe.services.SendersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
@RequestMapping(value = "/senders")
public class SenderController {

    @Autowired
    private SendersService sendersService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
        public ResponseEntity<List<Senders>> findAll() {
            List<Senders> sendersList = sendersService.findAll();
            return ResponseEntity.ok().body(sendersList);
        }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Senders> findById(@PathVariable UUID id) {
        Senders Senders = sendersService.findById(id);
        return ResponseEntity.ok().body(Senders);
    }

    @PostMapping
    public ResponseEntity<Senders> insert(@RequestBody /*@Valid*/ Senders Senders) {
        Senders = sendersService.insert(Senders);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Senders.getId()).toUri();
        return ResponseEntity.created(uri).body(Senders);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Senders> update(@PathVariable UUID id, @RequestBody @Valid Senders Senders) {
        Senders = sendersService.update(id, Senders);
        return ResponseEntity.ok().body(Senders);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        sendersService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
