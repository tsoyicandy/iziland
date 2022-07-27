package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.Procedural;
import com.itiad.iziland.models.entities.Processus;
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
public class ProcessusRestController {
    @Autowired
    private ProcessusRepository processusRepository;
    @Autowired
    private ProceduralRepository proceduralRepository;


    private Procedural getproceduralById(Long id){
        return proceduralRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cette procedure n'existe pas !!")));
    }

    @GetMapping("/processuses")
    public ResponseEntity<List<Processus>> getAllProcessus() {
        try {
            List<Processus> processuses = new ArrayList<Processus>();
            processuses = processusRepository.findAll();
            if (processuses.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(processuses, HttpStatus.OK);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/processuses/{id}")
    public ResponseEntity<List<Processus>> saveProcessuses(@PathVariable Long id, @RequestBody List<Processus> listprocessus) {
       try {
           Procedural procedural = getproceduralById(id);
           List<Processus> processusList = null;
           for (int i = 0 ; i<listprocessus.size(); i++) {
               listprocessus.get(i).setProcedural(procedural);
               Processus processus1 = processusRepository.save(listprocessus.get(i));
               processusList.add(processus1);
           }
           return new ResponseEntity<>(processusList, HttpStatus.CREATED);

       }catch (Exception e){
           return new ResponseEntity<>(null,  HttpStatus.valueOf(String.valueOf(e)));
       }
    }

    @GetMapping("/processuses/{id}")
    public ResponseEntity<Processus> getProcessusById(@PathVariable("id") Long id){
        Processus processus = processusRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce processus n'existe pas !!"))) ;
        return ResponseEntity.ok(processus);
    }

    @PutMapping("/processuses/{id}")
    public ResponseEntity<Processus> updateProcessus(@PathVariable Long id, @RequestBody Processus processus){
        Processus processusexistant = processusRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!"))) ;
        processusexistant.setId(id);
        processusexistant.setNom(processus.getNom());
        Processus processusupdated = processusRepository.save(processusexistant);

        return ResponseEntity.ok(processusupdated);
    }

    @DeleteMapping("/processuses/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProcessuses(@PathVariable Long id){
        Procedural procedural = proceduralRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cette proceduren'existe pas !!"))) ;
        processusRepository.deleteByProcedural(procedural);
        Map<String, Boolean> reponse = new HashMap<>();
        reponse.put("supprime", Boolean.TRUE);
        return ResponseEntity.ok(reponse);
    }

}
