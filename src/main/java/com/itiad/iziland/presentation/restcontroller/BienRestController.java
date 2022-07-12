package com.itiad.iziland.presentation.restcontroller;


import com.itiad.iziland.models.entities.Bien;
import com.itiad.iziland.repositories.BienRepository;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class BienRestController {

    @Autowired
    private BienRepository bienRepository;


    @GetMapping("/biens")
    public List<Bien> getAllBien() {
        return bienRepository.findAll();
    }

    @PostMapping("/biens")
    public Bien saveBien(@RequestBody Bien bien) {
        return bienRepository.save(bien);
    }


    @GetMapping("/biens/{id}")
    public ResponseEntity<Bien> getBienById(@PathVariable Long id){
        Bien bien = bienRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!"))) ;
        return ResponseEntity.ok(bien);
    }

    @PutMapping("/biens/{id}")
    public ResponseEntity<Bien> updateBien(@PathVariable Long id, @RequestBody Bien bien){
        Bien bienexistant = bienRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!"))) ;
        bienexistant.setId(id);
        bienexistant.setDescription(bien.getDescription());
        bienexistant.setEtat(bien.getEtat());
        bienexistant.setLatitude(bien.getLatitude());
        bienexistant.setLieu(bien.getLieu());
        bienexistant.setLongitude(bien.getLongitude());
        bienexistant.setNom(bien.getNom());
        bienexistant.setPrix(bien.getPrix());
        bienexistant.setProprietaire(bien.getProprietaire());
        bienexistant.setSuperficie(bien.getSuperficie());
        bienexistant.setTitre(bien.getTitre());
        bienexistant.setTypeBien(bien.getTypeBien());
        bienexistant.setTypeDeValorisation(bien.getTypeDeValorisation());

        Bien bienupdated = bienRepository.save(bienexistant);

        return ResponseEntity.ok(bienupdated);
    }


    @DeleteMapping("/biens/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBien(@PathVariable Long id){
        Bien bien = bienRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!"))) ;
        bienRepository.delete(bien);
        Map<String, Boolean> reponse = new HashMap<>();
        reponse.put("supprime", Boolean.TRUE);
        return ResponseEntity.ok(reponse);
    }
}