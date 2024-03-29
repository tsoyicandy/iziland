package com.itiad.iziland.repositories;

import com.itiad.iziland.models.entities.Etape;
import com.itiad.iziland.models.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface EtapeRepository extends JpaRepository<Etape, Long> {
    public  void deleteByTransaction(Transaction transaction);
    List<Etape> findByTransaction(Transaction transaction);
}
