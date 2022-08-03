package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.Procedural;
import com.itiad.iziland.repositories.ProceduralRepository;
import com.itiad.iziland.repositories.ProcessusRepository;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class ProceduralRestController {
    @Autowired
    private ProceduralRepository proceduralRepository;
    @Autowired
    private ProcessusRepository processusRepository;


    @GetMapping("/procedurals")
    public ResponseEntity<List<Procedural>> getAllProcedural() {
        try {
            List<Procedural> procedurals = new ArrayList<Procedural>();
            procedurals = proceduralRepository.findAll();
            if (procedurals.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(procedurals, HttpStatus.OK);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/procedurals")
    public ResponseEntity<Procedural> saveProcedural(@RequestBody Procedural procedural) {
        Procedural procedural1 = proceduralRepository.save(procedural);
        return new ResponseEntity<>(procedural1, HttpStatus.CREATED);

    }

    @GetMapping("/procedurals/{id}")
    public ResponseEntity<Procedural> getProceduralById(@PathVariable("id") Long id){
        Procedural procedural = proceduralRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!"))) ;
        return ResponseEntity.ok(procedural);
    }

    @GetMapping("/procedurals/{nomprocedural}")
    public Procedural getProceduralByNom(@PathVariable("nomprocedural") String nomprocedural){
        Procedural procedural = proceduralRepository.findByNom(nomprocedural).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!"))) ;
        return procedural;
    }

    @PutMapping("/procedurals/{id}")
    public ResponseEntity<Procedural> updateProcedural(@PathVariable Long id, @RequestBody Procedural procedural){
        Procedural procedural1 = proceduralRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cette procedure n'existe pas !!"))) ;
        procedural1.setId(id);
        procedural.setNom(procedural.getNom());
        Procedural proceduralupdated = proceduralRepository.save(procedural1);

        return ResponseEntity.ok(proceduralupdated);
    }

    @DeleteMapping("/procedurals/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProcedural(@PathVariable Long id){
        Procedural procedural = proceduralRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cette procedure n'existe pas !!"))) ;
        proceduralRepository.delete(procedural);
        processusRepository.deleteByProcedural(procedural);
        Map<String, Boolean> reponse = new HashMap<>();
        reponse.put("supprime", Boolean.TRUE);
        return ResponseEntity.ok(reponse);
    }

    @DeleteMapping("/procedurals")
    public ResponseEntity<HttpStatus> deleteAllProcedurals(){
        try{
            proceduralRepository.deleteAll();
            processusRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   }
