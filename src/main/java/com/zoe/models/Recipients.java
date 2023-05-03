package com.zoe.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_recipients")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipients extends SignUp implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "recipients", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipients_id")
    private Senders senders;

}
