package com.itiad.iziland.models.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Procuration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String nomBeneficiare;

    private String adresse;

    private String dateDeCreation;

    private String cni;

    private String numero;

    private String motif;

    private String qrcode;

    @ManyToOne
    @JoinColumn(name="utilisateur")
    private Utilisateur utilisateur;

}
