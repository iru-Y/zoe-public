package com.zoe.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zoe.repositories.ProductRepository;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_order")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private Instant date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "senders_id")
    private Senders senders;
    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "recipients_id")
    private Recipients recipients;

    private String tracker;

    @NotNull
    private String status;

    public static Order createOrder(UUID id, Long productId, EntityManager em, ProductRepository productRepository, String status) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Produto com id " + productId + " não encontrado.");
        }
        Product product = productOptional.get();
        Order order = new Order();
        em.persist(order);
        return order;
    }



    @PrePersist
    public void prePersist() {
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDateTime localDateTime = LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        this.date = zonedDateTime.toInstant();
        this.date = this.date.plusMillis(1);
        this.date = this.date.truncatedTo(ChronoUnit.SECONDS);
        this.date = this.date.minusMillis(this.date.get(ChronoField.MILLI_OF_SECOND));

        String prefix = "ZOE";
        String prefix1 = "777";
        Random random = new Random();
        long randomNum = random.nextLong(9000000) + 1000000;
        this.tracker = prefix + randomNum + prefix1;
    }
}