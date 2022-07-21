package com.itiad.iziland.models.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.nio.file.Path;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String url;
    public FileInfo(String nom, String lienDocument){
        this.name = nom;
        this.url = lienDocument;
    }

    private String typeDocument;

    @ManyToOne
    @JoinColumn(name="bien")
    private Bien bien;

    @ManyToOne
    @JoinColumn(name="etape")
    private Etape etape;


}
