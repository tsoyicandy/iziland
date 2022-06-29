package com.itiad.iziland.models.entities;


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
    private String typeBien;
    private String description;
    private String lieu;
    private Long longitude;
    private Long latitude;
    private Integer prix;
    private String typeDeValorisation;
    private Boolean titre;
    private String etat;
    @ManyToOne
    @JoinColumn(name="agence")
    private Agence agence;

    @OneToMany(mappedBy="bien")
    private List<Image> Listimage;

    @OneToMany(mappedBy="bien")
    private List<Transaction> Listtransactionbien;



}
