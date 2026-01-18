package com.vinicius.jobTrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "companyName", length = 100)
    private String companyName; // empresa
    @Column(name = "jobTitle", length = 100)
    private String jobTitle; // titulo
    @Enumerated(EnumType.STRING) // salva o nome do Enum no banco / persistido como String
    private ApplicationStatus status;
    @Column(name = "appliedAt", length = 100)
    private String appliedAt; // data e hora da candidatura
    @ManyToOne
    // cria uma coluna que referencia o id da tabela userId
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @JsonIgnore
    private Userr user;

}
