package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.Proprietaire;
import com.itiad.iziland.repositories.ProprietaireRepository;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class ProprietaireRestController {
    @Autowired
    private ProprietaireRepository proprietaireRepository;

    @GetMapping("/proprietaires")
    public List<Proprietaire> getAllProprietaire() {
        return proprietaireRepository.findAll();
    }

    @GetMapping("/proprietaires/{id}")
    public ResponseEntity<Proprietaire> getProprietaireById(@PathVariable Long id){
        Proprietaire proprietaire = proprietaireRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cet Utilisateur n'existe pas !!"))) ;
        return ResponseEntity.ok(proprietaire);
    }

    @PutMapping("/proprietaires/{id}")
    public ResponseEntity<Proprietaire> updateProprietaire(@PathVariable Long id, @RequestBody Proprietaire proprietaire){
        Proprietaire proprietaireExistant = proprietaireRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce Proprietaire n'existe pas !!"))) ;
        proprietaireExistant.setId(id);
        proprietaireExistant.setEmail(proprietaire.getEmail());
        proprietaireExistant.setNom(proprietaire.getNom());
        proprietaireExistant.setTelephone (proprietaire.getTelephone());
        proprietaireExistant.setAdresse(proprietaire.getAdresse());
        Proprietaire proprietaireupdated = proprietaireRepository.save(proprietaireExistant);

        return ResponseEntity.ok(proprietaireupdated);
    }


    @DeleteMapping("/proprietaires/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProprietaire(@PathVariable Long id){
        Proprietaire proprietaire = proprietaireRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce proprietaire n'existe pas !!"))) ;
        proprietaireRepository.delete(proprietaire);
        Map<String, Boolean> reponse = new HashMap<>();
        reponse.put("supprime", Boolean.TRUE);
        return ResponseEntity.ok(reponse);
    }

    @PostMapping("/proprietaires")
    public Proprietaire saveProprietaire(@RequestBody Proprietaire proprietaire) {
        return proprietaireRepository.save(proprietaire);
    }
}
