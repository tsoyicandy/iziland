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
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="utilisateur")
    private Utilisateur utilisateur;


    @OneToMany(mappedBy="discussion")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Message> Listmessage;

}
