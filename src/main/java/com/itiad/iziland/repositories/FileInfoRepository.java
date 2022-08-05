package com.itiad.iziland.repositories;

import com.itiad.iziland.models.entities.Bien;
import com.itiad.iziland.models.entities.Etape;
import com.itiad.iziland.models.entities.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

    List<FileInfo> findByTypeDocument(String typeDocument);
    List<FileInfo> findByBien(Bien bien);
    List<FileInfo> findByEtape(Etape etape);
    Optional<FileInfo> findByName(String nom);
}
