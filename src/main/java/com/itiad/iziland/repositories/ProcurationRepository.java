package com.itiad.iziland.repositories;


import com.itiad.iziland.models.entities.Procuration;
import com.itiad.iziland.models.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ProcurationRepository extends JpaRepository<Procuration, Long> {
    Optional<Procuration> findById(Long id);
    List<Procuration> findByUtilisateur(Utilisateur utilisateur);
}
