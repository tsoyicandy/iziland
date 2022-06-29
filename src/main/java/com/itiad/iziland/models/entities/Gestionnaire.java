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
@PrimaryKeyJoinColumn(name = "id")
public class Gestionnaire extends Utilisateur {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="agence")
    private Agence agence;

    @OneToMany(mappedBy="gestionnaire")
    private List<Document> Listdocument;

    @OneToMany(mappedBy="gestionnaire")
    private List<Client> Listclient;

    @OneToMany(mappedBy="gestionnaire")
    private List<Discussion> Listdiscussion;
}
