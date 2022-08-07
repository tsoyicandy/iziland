package com.itiad.iziland.services.serviceImpl;

import com.itiad.iziland.models.entities.*;
import com.itiad.iziland.repositories.*;
import com.itiad.iziland.services.Iservices.StatisqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatistiqueImpl implements StatisqueService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private BienRepository bienRepository;

    @Autowired
    private ProprietaireRepository proprietaireRepository;

    @Autowired
    private ProcurationRepository procurationRepository;







    @Override
    public Long getnombreTransactionEnCours() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions = transactionRepository.findByEtat("Traitement");
        return (long) transactions.size();
    }

    @Override
    public Long getnombreUtilisateursApplication() {
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        utilisateurs = utilisateurRepository.findAll();
        return (long) utilisateurs.size();
    }

    @Override
    public Long getnombreBiensDisponible() {
        List<Bien> biens = new ArrayList<Bien>();
        biens = bienRepository.findByEtat("disponible");
        return (long) biens.size();
    }

    @Override
    public Long getnombreBiensVendu() {
        List<Bien> biens = new ArrayList<Bien>();
        biens = bienRepository.findByEtat("vendu");
        return (long) biens.size();
    }

    @Override
    public Long getnombreTransactionTerminees() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions = transactionRepository.findByEtat("Termine");
        return (long) transactions.size();
    }

    @Override
    public Long getnombreProprietaire() {
        List<Proprietaire> proprietaires = new ArrayList<Proprietaire>();
        proprietaires = proprietaireRepository.findAll();
        return (long) proprietaires.size();
    }

    @Override
    public Long getnombreTerrainDisponible() {
        List<Bien> biens = new ArrayList<Bien>();
        biens = bienRepository.findByTypeBienAndEtat("Terrain","disponible");
        return (long) biens.size();
    }


    @Override
    public Long getnombreTransactionAnnulee() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions = transactionRepository.findByEtat("Annule");
        return (long) transactions.size();
    }

    @Override
    public Long getnombreProcurationGeneree() {
        List<Procuration> procurations = new ArrayList<Procuration>();
        procurations = procurationRepository.findAll();
        return (long) procurations.size();
    }
}
