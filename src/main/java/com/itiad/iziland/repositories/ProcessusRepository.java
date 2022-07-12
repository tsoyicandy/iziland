package com.itiad.iziland.repositories;

import com.itiad.iziland.models.entities.Processus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProcessusRepository extends JpaRepository<Processus, Long> {
}
