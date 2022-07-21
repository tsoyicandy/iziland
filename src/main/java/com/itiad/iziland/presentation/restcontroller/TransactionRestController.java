package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.Transaction;
import com.itiad.iziland.repositories.TransactionRepository;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class TransactionRestController {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/transactions")
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
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
        Transaction transactionupdated = transactionRepository.save(tr);

        return ResponseEntity.ok(transactionupdated);
    }



    @PostMapping("/transactions")
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
