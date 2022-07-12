package com.itiad.iziland.repositories;

import com.itiad.iziland.models.entities.Procedural;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProceduralRepository extends JpaRepository<Procedural, Long> {
}
