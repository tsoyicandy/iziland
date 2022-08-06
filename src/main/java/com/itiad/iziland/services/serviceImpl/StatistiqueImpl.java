package com.itiad.iziland.services.serviceImpl;

import com.itiad.iziland.models.entities.*;
import com.itiad.iziland.repositories.*;
import com.itiad.iziland.services.Iservices.StatisqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    List<Transaction> transactions = null;
    List<Utilisateur> utilisateurs = null;
    List<Proprietaire> proprietaires = null;
    List<Bien> biens = null;
    List<Procuration> procurations = null;

    @Override
    public Long getnombreTransactionEnCours() {
        transactions = transactionRepository.findByEtat("Traitement");
        return (long) transactions.size();
    }

    @Override
    public Long getnombreUtilisateursApplication() {
        utilisateurs = utilisateurRepository.findAll();
        return (long) utilisateurs.size();
    }

    @Override
    public Long getnombreBiensDisponible() {
        biens = bienRepository.findByEtat("disponible");
        return (long) biens.size();
    }

    @Override
    public Long getnombreBiensVendu() {
        biens = bienRepository.findByEtat("vendu");
        return (long) biens.size();
    }

    @Override
    public Long getnombreTransactionTerminees() {
        transactions = transactionRepository.findByEtat("Termine");
        return (long) transactions.size();
    }

    @Override
    public Long getnombreProprietaire() {
        proprietaires = proprietaireRepository.findAll();
        return (long) transactions.size();
    }

    @Override
    public Long getnombreTerrain() {
        biens = bienRepository.findByTypeBienAndEtat("Terrain","disponible");
        return (long) biens.size();
    }


    @Override
    public Long getnombreTransactionAnnulee() {
        transactions = transactionRepository.findByEtat("Annule");
        return (long) transactions.size();
    }

    @Override
    public Long getnombreProcurationGeneree() {
        procurations = procurationRepository.findAll();
        return (long) procurations.size();
    }
}
