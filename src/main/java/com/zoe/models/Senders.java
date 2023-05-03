package com.zoe.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_senders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Senders extends SignUp implements Serializable {
    @Serial
    private static final long serialVersionUID= 1L;

    private String cpf;

    @NotNull
    private String mail;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String productDescription;


    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "senders",  cascade = CascadeType.ALL)
    private List<Order> order = new ArrayList<>();

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "senders_id")
    private Recipients recipients;

}

