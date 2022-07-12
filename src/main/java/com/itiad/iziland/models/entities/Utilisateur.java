package com.itiad.iziland.models.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itiad.iziland.models.AppUtilisateurRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "utilisateur",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email") })
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "votre nom est obligatoire")
    private String nom;
    @NotBlank(message = "votre prenom est obligatoire")
    private String prenom;
    @Email(message = "entrez un mail correct")
    private String email;
    @NotBlank(message = "votre mot de passe est obligatoire")
    private String motdepasse;
    @NotBlank(message = "votre numero de telephone est obligatoire")
    private String telephone;
    @ManyToMany
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role = new HashSet<>();


    @OneToMany(mappedBy="utilisateur")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Procuration> Listprocuration;

    @OneToMany(mappedBy="utilisateur")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Transaction> Listtransaction;

    @OneToMany(mappedBy="utilisateur")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Document> Listdocument;

    @OneToMany(mappedBy="utilisateur")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Discussion> Listdiscussion;



    public Utilisateur(String nom, String prenom, String email, String encode, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motdepasse = encode;
        this.telephone = telephone;
    }
}
