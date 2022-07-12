package com.itiad.iziland.services.serviceImpl;


import com.itiad.iziland.models.entities.Bien;
import com.itiad.iziland.repositories.BienRepository;
import com.itiad.iziland.services.Iservices.Iagence;
import com.itiad.iziland.services.Iservices.Ibien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class BienImpl implements Ibien {

    @Autowired
    private BienRepository bienRepository;

    public BienImpl(BienRepository bienRepository) {
        this.bienRepository = bienRepository;
    }

    @Override
    public List<Bien> getAllBien() {
        return bienRepository.findAll();
    }

    @Override
    public Bien saveBien(Bien bien) {
        return bienRepository.save(bien);
    }

    @Override
    public Bien updateBien(Bien bien) {
        return bienRepository.save(bien) ;
    }

    @Override
    public void deleteBienById(Long id) {
        bienRepository.deleteById(id);
    }

    @Override
    public Bien getBienById(Long id) {
        return bienRepository.findById(id).get();
    }


}
