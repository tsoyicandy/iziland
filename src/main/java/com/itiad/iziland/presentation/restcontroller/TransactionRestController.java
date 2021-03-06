package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.*;
import com.itiad.iziland.repositories.*;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class TransactionRestController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProceduralRepository proceduralRepository;
    @Autowired
    private BienRepository bienRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private ProcurationRepository procurationRepository;

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @GetMapping("/transactions/parEtat/{etat}")
    public ResponseEntity<List<Transaction>> getTransactionsByEtat(@PathVariable String etat) {
        try {
            List<Transaction> transactions = new ArrayList<Transaction>();
            transactions = transactionRepository.findByEtat(etat);
            if (transactions.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/transactions/parProcedural/{idprocedural}")
    public ResponseEntity<List<Transaction>> getTransactionsByProcedural(@PathVariable Long idprocedural) {
        try {
            List<Transaction> transactions = new ArrayList<Transaction>();
            Optional<Procedural> procedural = proceduralRepository.findById(idprocedural);
            transactions = transactionRepository.findByProcedural(procedural);
            if (transactions.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cet Utilisateur n'existe pas !!"))) ;
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction){
        Transaction tr = transactionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("wrong transaction !!"))) ;
        tr.setEtapeEnCours(transaction.getEtapeEnCours());
        tr.setEtat(transaction.getEtat());
        tr.setDateFin(transaction.getDateFin());
        Transaction transactionupdated = transactionRepository.save(tr);

        return ResponseEntity.ok(transactionupdated);
    }

    @PutMapping("/transactions/{id}/{idprocuration}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id,@PathVariable Long idprocuration, @RequestBody Transaction transaction){
        Transaction tr = transactionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("wrong transaction !!"))) ;
        tr.setProcuration(getProcurationById(idprocuration));
        Transaction transactionupdated = transactionRepository.save(tr);

        return ResponseEntity.ok(transactionupdated);
    }

    @PostMapping("/transactions/{iduser}/{idbien}/{idprocedural}")
    public Transaction saveTransaction(@PathVariable("iduser") Long iduser,@PathVariable("idbien") Long idbien,@PathVariable("idprocedural") Long idprocedural, @RequestBody Transaction transaction) {
    transaction.setEtapeEnCours("Aucune");
    transaction.setProcedural(getProceduralById(idprocedural));
    transaction.setBien(getbienById(iduser));
    transaction.setUtilisateur(getUtilisateurById(iduser));

        return transactionRepository.save(transaction);
    }

    private Procedural getProceduralById(Long id){
        return proceduralRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cette Procedure n'existe pas !!")));
    }
    private Bien getbienById(Long id){
        return bienRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!")));
    }
    private Utilisateur getUtilisateurById(Long id){
        return utilisateurRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!")));
    }
    private Procuration getProcurationById(Long id){
        return procurationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!")));
    }
}
