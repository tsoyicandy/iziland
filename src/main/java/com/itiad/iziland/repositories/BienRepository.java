package com.itiad.iziland.repositories;


import com.itiad.iziland.models.entities.Bien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BienRepository extends JpaRepository<Bien, Long> {

}
