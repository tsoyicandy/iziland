package com.itiad.iziland.repositories;


import com.itiad.iziland.models.entities.Bien;
import com.itiad.iziland.models.entities.Proprietaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ProprietaireRepository extends JpaRepository<Proprietaire, Long> {

    List<Proprietaire> findAll();

    Optional<Proprietaire> findById(Long id);


}
