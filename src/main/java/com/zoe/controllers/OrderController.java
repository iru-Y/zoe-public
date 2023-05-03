package com.zoe.controllers;

import com.zoe.exceptions.ResourceNotFoundException;;
import com.zoe.models.Order;
import com.zoe.services.EmailService;
import com.zoe.services.OrderService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/order")
    public ResponseEntity<List<Order>> findAll() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok().body(orders);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/order/{id}")
    public ResponseEntity<Order> findById(@PathVariable UUID id) {
        Order orders = orderService.findById(id);
        return ResponseEntity.ok().body(orders);
    }

    @PostMapping(value = "/order")
    public ResponseEntity<Order> insert(@RequestBody Order order) throws MessagingException {
        order = orderService.insert(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(order.getId()).toUri();
        emailService.sendEmail(order);
        return ResponseEntity.created(uri).body(order);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/order")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            orderService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/order/{id}")
    public ResponseEntity<Order> update(@PathVariable UUID id, @RequestBody Order order) {
        order = orderService.update(id, order);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping(value = "/tracker/{tracker}")
    public ResponseEntity<Order> findByTracker(@PathVariable String tracker) {
        try {
            Order order = orderService.findByTracker(tracker);
            return ResponseEntity.ok().body(order);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Código de rastreio inexistente, ou não encontrado no sistema.");
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/tracker/{tracker}")
    public ResponseEntity<Void> deleteByTracker(@PathVariable String tracker) {
        try {
            orderService.deleteByTracker(tracker);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
