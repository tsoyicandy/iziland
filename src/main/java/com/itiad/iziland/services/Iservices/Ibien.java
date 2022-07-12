package com.itiad.iziland.services.Iservices;

import com.itiad.iziland.models.entities.Bien;

import java.util.List;

public interface Ibien {
    List<Bien> getAllBien();

    Bien saveBien(Bien bien);

    Bien getBienById(Long id);

    Bien updateBien(Bien bien);

    void deleteBienById(Long id);

}
