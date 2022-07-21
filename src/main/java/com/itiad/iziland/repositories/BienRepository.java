package com.itiad.iziland.repositories;


import com.itiad.iziland.models.entities.Bien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface BienRepository extends JpaRepository<Bien, Long> {

    List<Bien> findAll();

    Optional<Bien> findById(Long id);


    List<Bien> findByNomContainingIgnoreCaseAndEtat( String nom,String etat);

    List<Bien> findByLieuContainingIgnoreCaseAndEtat(String lieu, String etat);

    List<Bien> findByTypeBienAndEtat(String typeBien, String etat);

    List<Bien> findByEtat(String etat);

    List<Bien> findBySuperficieGreaterThanEqualAndEtat(Long superficie, String etat);

    List<Bien> findByPrixGreaterThanEqualAndEtat(Long prix, String etat);

    List<Bien> findBySuperficieLessThanEqualAndEtat(Long superficie, String etat);

    List<Bien> findByPrixLessThanEqualAndEtat(Long prix, String etat);

}
