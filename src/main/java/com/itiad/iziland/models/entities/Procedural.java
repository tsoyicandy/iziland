
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
public class Procedural {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  String nom;
    /*@OneToMany(mappedBy="procedural")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Transaction> Listtransaction;*/


    @OneToMany(mappedBy="procedural")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Processus> Listprocessus;
}
