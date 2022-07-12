package com.itiad.iziland.services.serviceImpl;

import com.itiad.iziland.repositories.BienRepository;
import com.itiad.iziland.services.Iservices.IizilandInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IzilandInitServiceImpl implements IizilandInitService {

    @Autowired
    private BienRepository bienRepository;
/*

    @Override
    public void initBien() {
        agenceRepository.findAll().forEach(v->{
            Stream.of("Kambuso Appartments","land of Humbles").forEach(nomBien->{
                Bien bien = new Bien();
                bien.setNom(nomBien);
                bien.setAgence(v);
                bienRepository.save(bien);
            });
        });

    }*/

    @Override
    public void initClient() {

    }

    @Override
    public void initDiscussion() {

    }

    @Override
    public void initDocument() {

    }

    @Override
    public void initGestionnaire() {

    }

    @Override
    public void initImage() {

    }

    @Override
    public void initMessage() {

    }

    @Override
    public void initProcedural() {

    }

    @Override
    public void initProcessus() {

    }

    @Override
    public void initProcuration() {

    }

    @Override
    public void initTransaction() {

    }

    @Override
    public void initTypeDocument() {

    }

    @Override
    public void initUtilisateur() {

    }
}
