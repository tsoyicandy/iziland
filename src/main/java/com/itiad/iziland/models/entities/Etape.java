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
public class Etape {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String dateFin;

    private String dateDebut;

    private String etat;

    @ManyToOne
    @JoinColumn(name="transaction")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name="processus")
    private Processus processus;

    @OneToMany(mappedBy="etape")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<FileInfo> listdocument;
}
