package com.vinicius.jobTrack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Userr {

    // Aqui geramos ID aleatórios atraves do GeneratedValue
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aqui geramos nossas colunas do DB
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "email", length = 100, nullable = false, unique = true) // faz o campo obrigatório e email único
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // será visivel apenas na entrada e nunca na saida
    @Column(name = "password") // faz o campo obrigatório
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs;

}
