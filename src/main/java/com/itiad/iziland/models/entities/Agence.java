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
public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String mail;
    private String lieu;
    private String description;
    private  String contact;
    private String politique;

    @OneToMany(mappedBy="agence")
    private List<Gestionnaire> Listgestionnaire;

    @OneToMany(mappedBy="agence")
    private List<Bien> Listbien;
}
