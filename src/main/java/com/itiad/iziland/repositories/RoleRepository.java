package com.itiad.iziland.repositories;

import com.itiad.iziland.models.AppUtilisateurRole;
import com.itiad.iziland.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Role findByName(AppUtilisateurRole name);
}
