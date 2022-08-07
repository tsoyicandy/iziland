package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.*;
import com.itiad.iziland.repositories.EtapeRepository;
import com.itiad.iziland.repositories.ProcessusRepository;
import com.itiad.iziland.repositories.ProprietaireRepository;
import com.itiad.iziland.repositories.TransactionRepository;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class EtapeRestController {

    @Autowired
    private EtapeRepository etapeRepository ;

    @Autowired
    private ProprietaireRepository proprietaireRepository;
    @Autowired
    private ProcessusRepository processusRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    private String nomProcessus(Transaction transaction, Long position){
        List<Processus> processusList =  processusRepository.findProcessusesByProceduralOrderByNumeroEtapeAsc(transaction.getProcedural());
        return processusRepository.findByNumeroEtape(position).get().getNom();
    }

    private String message="";

 /*   @GetMapping("/etapes")
    public ResponseEntity<List<Etape>> getAllEtapes() {
        try {
            List<Etape> etapes = new ArrayList<Etape>();
            etapes = etapeRepository.findAll();
            if (etapes.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(etapes, HttpStatus.OK);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
*/

    @GetMapping("/etapes/une/{id}")
    public ResponseEntity<Etape> getEtapesById(@PathVariable("id") Long id){
        Etape etape = etapeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cette etape n'existe pas !!"))) ;
        return ResponseEntity.ok(etape);
    }

    @GetMapping("/etapes/transaction/{idtransaction}")
    public ResponseEntity<List<Etape>> getEtapesByTransaction(@PathVariable("idtransaction") Long idtransaction){
        List<Etape> etapes = etapeRepository.findByTransaction(getTransactionById(idtransaction)) ;
        if (!etapes.isEmpty())
        {
            return ResponseEntity.ok(etapes);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/etapes/{id}")
    @PreAuthorize(" hasRole('GESTIONNAIRE')")
    public ResponseEntity<Etape> updateEtape(@PathVariable Long id ){
        Etape etape = new Etape();
        try {
            Etape etape1 = etapeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("wrong transaction !!"))) ;
            Transaction transaction = etape1.getTransaction();
            etape1.setEtat("Termine");
            etape1.setDateFin(String.valueOf(Date.valueOf(String.valueOf(LocalDateTime.now()))));
            etapeRepository.save(etape1);
            if (transaction.getEtapeEnCours() < transaction.getNombreDeProcessus()){

                transaction.setEtapeEnCours(transaction.getEtapeEnCours()+1);
                etape.setTransaction(transaction);
                etape.setDateDebut(String.valueOf(Date.valueOf(String.valueOf(LocalDateTime.now()))));
                etape.setDateFin("-");
                etape.setEtat("Traitement");
                etape.setNom(nomProcessus(transaction,transaction.getEtapeEnCours()));
                Etape step = etapeRepository.save(etape);
                message="Etape suivante entamee";
                return ResponseEntity.ok(step);
            }else if (transaction.getEtapeEnCours() == transaction.getNombreDeProcessus()){
                transaction.setEtapeEnCours(transaction.getEtapeEnCours()+1);
                transaction.setDateFin(String.valueOf(Date.valueOf(String.valueOf(LocalDateTime.now()))));
                transaction.setEtat("Termine");
                Utilisateur utilisateur = transaction.getUtilisateur();
                Proprietaire proprietaire = new Proprietaire();
                proprietaire.setNom(utilisateur.getNom()+" "+utilisateur.getPrenom());
                proprietaire.setEmail(utilisateur.getEmail());
                proprietaire.setTelephone(utilisateur.getTelephone());
                proprietaire.setAdresse("-");
                proprietaireRepository.save(proprietaire);
                transaction.getBien().setProprietaire(proprietaire);
                transaction.getBien().setEtat("vendu");
                transactionRepository.save(transaction);
                message= "Traitement termine";
                return ResponseEntity.ok(etape);
            }else {
                message="Cette transaction est deja terminee";
                return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

    }

    private Processus getProcessusById(Long id){
        return processusRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce processus n'existe pas !!")));
    }

    private Transaction getTransactionById(Long id){
        return transactionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce processus n'existe pas !!")));
    }

}
