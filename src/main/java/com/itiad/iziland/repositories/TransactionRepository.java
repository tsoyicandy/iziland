package com.itiad.iziland.repositories;

import com.itiad.iziland.models.entities.Procedural;
import com.itiad.iziland.models.entities.Transaction;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
   List<Transaction> findByEtat(String etat);
   List<Transaction> findByProcedural(Optional<Procedural> procedural);

}
