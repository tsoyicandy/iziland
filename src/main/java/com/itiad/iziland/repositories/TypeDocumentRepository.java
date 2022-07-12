package com.itiad.iziland.repositories;

import com.itiad.iziland.models.entities.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TypeDocumentRepository extends JpaRepository<TypeDocument, Long> {
}
