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
    private  String date;

    @ManyToOne
    @JoinColumn(name="discussion")
    private Discussion discussion;

    @ManyToOne
    @JoinColumn(name="auteur")
    private Utilisateur auteur;



}
