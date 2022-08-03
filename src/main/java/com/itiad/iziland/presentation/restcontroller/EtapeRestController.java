package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.Etape;
import com.itiad.iziland.models.entities.Processus;
import com.itiad.iziland.models.entities.Transaction;
import com.itiad.iziland.repositories.EtapeRepository;
import com.itiad.iziland.repositories.ProcessusRepository;
import com.itiad.iziland.repositories.TransactionRepository;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class EtapeRestController {

    @Autowired
    private EtapeRepository etapeRepository;
    @Autowired
    private ProcessusRepository processusRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    @GetMapping("/etapes")
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

    @PostMapping("/etapes/{idtransaction}/{idprocessus}")
    public ResponseEntity<Etape> saveEtape(@PathVariable("idtransaction") Long idtransaction,@PathVariable("idprocessus") Long idprocessus, @RequestBody Etape etape) {
        etape.setProcessus(getProcessusById(idprocessus));
        etape.setTransaction(getTransactionById(idtransaction));
        Etape etape1 = etapeRepository.save(etape);
        return new ResponseEntity<>(etape1, HttpStatus.CREATED);
    }

    @GetMapping("/etapes/{id}")
    public ResponseEntity<Etape> getEtapeById(@PathVariable("id") Long id){
        Etape etape = etapeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!"))) ;
        return ResponseEntity.ok(etape);
    }

    @PutMapping("/etapes/{id}")
    public ResponseEntity<Etape> updateTransaction(@PathVariable Long id, @RequestBody Etape etape){
        Etape etape1 = etapeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("wrong transaction !!"))) ;
        etape1.setEtat(etape.getEtat());
        etape1.setDateFin(etape.getDateFin());
        Etape etapeupdated = etapeRepository.save(etape1);

        return ResponseEntity.ok(etapeupdated);
    }

    private Processus getProcessusById(Long id){
        return processusRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!")));
    }

    private Transaction getTransactionById(Long id){
        return transactionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!")));
    }

}
