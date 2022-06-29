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
public class Client extends Utilisateur {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="gestionnaire")
    private Gestionnaire gestionnaire;

    @OneToMany(mappedBy="client")
    private List<Procuration> Listprocuration;

    @OneToMany(mappedBy="client")
    private List<Transaction> Listtransaction;


}
