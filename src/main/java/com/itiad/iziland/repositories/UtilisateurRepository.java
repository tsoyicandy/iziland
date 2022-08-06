package com.itiad.iziland.repositories;

import com.itiad.iziland.models.entities.Role;
import com.itiad.iziland.models.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

    Optional<Utilisateur> findByEmail(String username);
    List<Utilisateur> findUtilisateurByGestionnaire(Utilisateur utilisateur);
    Utilisateur findByRole(Role role);
    Boolean existsByNom(String nom);
    Boolean existsByEmail(String email);
    boolean existsById(Long id);
}
