package com.itiad.iziland.models.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dateFin;
    private String dateDebut;
    private String etat;


    private String etapeEnCours;

    @ManyToOne
    @JoinColumn(name="procuration")
    private Procuration procuration;

    @ManyToOne
    @JoinColumn(name="procedural")
    private Procedural procedural;

    @ManyToOne
    @JoinColumn(name="bien")
    private Bien bien;

    @ManyToOne
    @JoinColumn(name="utilisateur")
    private Utilisateur utilisateur;

}
