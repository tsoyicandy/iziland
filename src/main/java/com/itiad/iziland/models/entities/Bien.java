package com.itiad.iziland.models.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Bien {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long superficie;
    private String nom;
    private String typeBien;
    private String description;
    private String lieu;
    private String proprietaire;
    private Long longitude;
    private Long latitude;
    private Integer prix;
    private String typeDeValorisation;
    private Boolean titre;
    private String etat;

    @OneToMany(mappedBy="bien")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Image> Listimage;

    @OneToMany(mappedBy="bien")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Transaction> Listtransactionbien;



}
