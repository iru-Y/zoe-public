package com.zoe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SignUp implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String fullName;

    @NotNull
    private String phone;

    @NotNull
    private String city;

    @NotNull
    private String district;

    @NotNull
    private String street;

    @NotNull
    private Integer number;

    @NotNull
    private String AddressLine2;

    @NotNull
    private String landmark;

}
