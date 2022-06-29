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
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dateFin;
    private String dateDebut;
    private String etat;

    @OneToMany(mappedBy="transaction")
    private List<Document> Listdocument;

    @ManyToOne
    @JoinColumn(name="procedural")
    private Procedural procedural;
    @ManyToOne
    @JoinColumn(name="bien")
    private Bien bien;
    @ManyToOne
    @JoinColumn(name="client")
    private Client client;


}
