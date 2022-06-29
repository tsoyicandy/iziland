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
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="client")
    private Client client;

    @ManyToOne
    @JoinColumn(name="gestionnaire")
    private Gestionnaire gestionnaire;

    @OneToMany(mappedBy="discussion")
    private List<Message> Listmessage;

}
