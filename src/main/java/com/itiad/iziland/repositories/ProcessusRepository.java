package com.itiad.iziland.repositories;

import com.itiad.iziland.models.entities.Procedural;
import com.itiad.iziland.models.entities.Processus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ProcessusRepository extends JpaRepository<Processus, Long> {
    void deleteByProcedural(Procedural procedural);
    List<Processus> findProcessusesByProceduralOrderByNumeroEtapeAsc(Procedural procedural);


    Optional<Processus> findByNumeroEtape(Long aLong);
}
