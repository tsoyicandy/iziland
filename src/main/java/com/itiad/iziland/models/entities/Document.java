package com.itiad.iziland.models.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String lienDocument;
    @ManyToOne
    @JoinColumn(name="typeDocument")
    private TypeDocument typeDocument;
    @ManyToOne
    @JoinColumn(name="gestionnaire")
    private Gestionnaire gestionnaire;
    @ManyToOne
    @JoinColumn(name="transaction")
    private Transaction transaction;

}
