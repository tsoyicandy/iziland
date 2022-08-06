package com.itiad.iziland.models.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Processus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String description;
    private Long numeroEtape;

    @ManyToOne
    @JoinColumn(name="procedural")
    private Procedural procedural;
}
