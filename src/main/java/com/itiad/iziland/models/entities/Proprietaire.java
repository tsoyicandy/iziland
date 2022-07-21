package com.itiad.iziland.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Proprietaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "votre nom est obligatoire")
    private String nom;

    @Email(message = "entrez un mail correct")
    private String email;

    @NotBlank(message = "votre numero de telephone est obligatoire")
    private String telephone;

    private String adresse;

    @OneToMany(mappedBy="proprietaire")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Bien> ListBien;
}
