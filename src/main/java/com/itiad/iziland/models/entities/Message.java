package com.itiad.iziland.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String contenu;

    @ManyToOne
    @JoinColumn(name="discussion")
    private Discussion discussion;
    @ManyToOne
    @JoinColumn(name="utilisateur")
    private Utilisateur auteur;

    @Override
    public String toString() {
        return "Message{" +
                "contenu='" + contenu + '\'' +
                ", auteur=" + auteur.getNom() +
                '}';
    }
}
