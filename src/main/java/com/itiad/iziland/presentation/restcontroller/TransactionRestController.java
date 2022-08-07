package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.*;
import com.itiad.iziland.repositories.*;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class TransactionRestController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProcessusRepository processusRepository;

    @Autowired
    private ProceduralRepository proceduralRepository;
    @Autowired
    private BienRepository bienRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private ProcurationRepository procurationRepository;
    @Autowired
    private EtapeRepository etapeRepository;

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cet Utilisateur n'existe pas !!"))) ;
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/transactions/parClient/{idutilisateur}")
    public ResponseEntity<List<Transaction>> getTransactionsByUser(@PathVariable("idutilisateur") Long idutilisateur){
        try{
        Utilisateur utilisateur = getUtilisateurById(idutilisateur);
        List<Transaction> transactions = transactionRepository.findByUtilisateurAndEtat(utilisateur, "Traitement");
        transactions.addAll(transactionRepository.findByUtilisateurAndEtat(utilisateur, "Termine"));
        if (transactions.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }
    }catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping("/transactions/parGestionnaire/{idutilisateur}")
    public ResponseEntity<List<Transaction>> getTransactionsByGestionnaire(@PathVariable("idutilisateur") Long idutilisateur){
        try{
            Utilisateur utilisateur = getUtilisateurById(idutilisateur);
            List<Utilisateur> utilisateurs = utilisateurRepository.findUtilisateurByGestionnaire(utilisateur);
            List<Transaction> transactions = null;
           for (int i=0; i<utilisateurs.size();i++){

               transactions = transactionRepository.findByUtilisateurAndEtat(utilisateurs.get(i), "Traitement");
               transactions.addAll(transactionRepository.findByUtilisateurAndEtat(utilisateurs.get(i), "Termine"));
           }
            if (transactions.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   /* @PutMapping("/transactions/terminate/{id}")
    public ResponseEntity<String> terminateTransaction(@PathVariable Long id){
        try {
            Transaction tr = transactionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("wrong transaction !!"))) ;
            if (tr.getEtapeEnCours()==tr.getNombreDeProcessus()){
                tr.setDateFin(String.valueOf(Date.valueOf(LocalDate.now())));
                tr.setDateFin("-");
                tr.setEtat("Termine");
                Utilisateur utilisateur = tr.getUtilisateur();
                Proprietaire proprietaire = new Proprietaire();
                proprietaire.setNom(utilisateur.getNom()+" "+utilisateur.getPrenom());
                proprietaire.setEmail(utilisateur.getEmail());
                proprietaire.setTelephone(utilisateur.getTelephone());
                tr.getBien().setProprietaire(proprietaire);
                transactionRepository.save(tr);
                return ResponseEntity.ok("la transaction est terminee le bien est à votre nom a present ");
            }else {
                return ResponseEntity.ok("la transaction n'est pas terminee encore des etapes a suivre ");
            }
        }catch (Exception e){
            return ResponseEntity.ok("la transaction n'a pas pu être annulee ");
        }

    }*/

    @GetMapping("/transactions/{iduser}/{idbien}/{idprocuration}")
    public ResponseEntity<Transaction> saveTransaction(@PathVariable("iduser") Long iduser, @PathVariable("idbien") Long idbien, @PathVariable("idprocuration") Long idprocuration) {


            Transaction transaction = new Transaction();
            Utilisateur utilisateur = utilisateurRepository.findById(iduser).get();
            Bien bien = bienRepository.findById(idbien).get();
            Procuration procuration = procurationRepository.findById(idprocuration).get();
            Procedural procedural = getProceduralByNom("Acheter");
            try {
                transaction.setUtilisateur(utilisateur);
                transaction.setBien(bien);
                transaction.setProcuration(procuration);
                transaction.setProcedural(procedural);
                transaction.setEtat("Traitement");
                bien.setEtat("indisponible");
                //transaction.setBien(bien);
                transaction.setDateDebut(String.valueOf(Date.valueOf(LocalDate.now())));
                transaction.setDateFin("-");
                //transaction.setUtilisateur(getUtilisateurById(iduser));
                transaction.setNombreDeProcessus(((long) transaction.getProcedural().getListprocessus().size() == 0 )? 0 :(long) transaction.getProcedural().getListprocessus().size() );
                //transaction.setProcuration(getProcurationById(idprocuration));
                transaction.setEtapeEnCours(1L);
                System.out.println(transaction.getEtapeEnCours());
                Transaction tr = transactionRepository.save(transaction);
                Etape etape = new Etape();
                etape.setTransaction(tr);
                etape.setDateDebut(String.valueOf(Date.valueOf(LocalDate.now())));
                etape.setDateFin("-");
                etape.setEtat("Traitement");
                etape.setNom(nomProcessus(tr,tr.getEtapeEnCours()));
                etapeRepository.save(etape);
                return ResponseEntity.ok(tr);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<String> abortTransaction(@PathVariable("id") Long id) {
        try {
            Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cette transaction n'existe pas !!"))) ;
            if (transaction.getEtapeEnCours() < transaction.getNombreDeProcessus()-1){
                transaction.setEtat("Annule");
                transaction.getBien().setEtat("disponible");
                return ResponseEntity.ok("Procuration annulee avec succes");
            }else {
                return ResponseEntity.ok("Impossible d'annuler la transaction");
            }

        }catch (Exception e){
            return ResponseEntity.ok("Impossible d'annuler la transaction");
        }
    }

    private Procedural getProceduralByNom(String nom){
        return proceduralRepository.findByNom(nom).orElseThrow(()-> new ResourceNotFoundException(("cette Procedure n'existe pas !!")));
    }
    private Bien getbienById(Long id){
        return bienRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!")));
    }
    private Utilisateur getUtilisateurById(Long id){
        return utilisateurRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cet utilisateur n'existe pas !!")));
    }
    private Procuration getProcurationById(Long id){
        return procurationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!")));
    }
    private String nomProcessus(Transaction transaction, Long position){
       List<Processus> processusList =  processusRepository.findProcessusesByProceduralOrderByNumeroEtapeAsc(transaction.getProcedural());
        return processusRepository.findByNumeroEtape(position).get().getNom();
    }

}






