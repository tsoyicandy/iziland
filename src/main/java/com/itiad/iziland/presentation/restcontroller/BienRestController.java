package com.itiad.iziland.presentation.restcontroller;


import com.itiad.iziland.models.entities.Bien;
import com.itiad.iziland.repositories.BienRepository;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/")
public class BienRestController {

    @Autowired
    private BienRepository bienRepository;


    @GetMapping("/biens")
    public ResponseEntity< List<Bien>> getAllBien() {
        try {
            List<Bien> biens = new ArrayList<Bien>();
            biens = bienRepository.findByEtat("disponible");
            if (biens.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(biens, HttpStatus.OK);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/biens")
    public ResponseEntity<Bien> saveBien(@RequestBody Bien bien) {
       Bien bien1 = bienRepository.save(bien);
        return new ResponseEntity<>(bien1, HttpStatus.CREATED);

    }

    @GetMapping("/biens/{id}")
    public ResponseEntity<Bien> getBienById(@PathVariable("id") Long id){
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

    @DeleteMapping("/biens")
    public ResponseEntity<HttpStatus> deleteAllBiens(){
        try{
            bienRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/biens/listedesbiens/{listedesbiens}")
    public ResponseEntity<HttpStatus> deleteAllBiens(@PathVariable("listedesbiens")  List<Bien> listedesbiens ){
        try{
            bienRepository.deleteAllInBatch(listedesbiens);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/biens/recherchenom/{nomrecherche}")
    public ResponseEntity<List<Bien>> findAllBiens(@PathVariable("nomrecherche") String nomrecherche){
        try {
            List<Bien> bienData = bienRepository.findByNomContainingIgnoreCaseAndEtat(nomrecherche,"disponible");
            if (bienData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(bienData, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/biens/touslesbiens")
    public ResponseEntity<List<Bien>> getEveryBien(){
        try {
            List<Bien> bienData = bienRepository.findAll();
            if (bienData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(bienData, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/biens/recherchelieu/{lieurecherche}")
    public ResponseEntity<List<Bien>> findByLieuContainingIgnoreCaseAndEtat(@PathVariable("lieurecherche") String lieurecherche){
        try {
            List<Bien> bienData = bienRepository.findByLieuContainingIgnoreCaseAndEtat(lieurecherche,"disponible");
            if (bienData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(bienData, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/biens/recherchetypeBien/{typeBienrecherche}")
    public ResponseEntity<List<Bien>> findByTypeBienAndEtat(@PathVariable("typeBienrecherche") String typeBienrecherche){
        try {
            List<Bien> bienData = bienRepository.findByTypeBienAndEtat(typeBienrecherche,"disponible");
            if (bienData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(bienData, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/biens/recherchesuperficiemin/{superficierecherche}")
    public ResponseEntity<List<Bien>> findBySuperficieGreaterThanEqualAndEtat(@PathVariable("superficierecherche") Long superficierecherche){
        try {
            List<Bien> bienData = bienRepository.findBySuperficieGreaterThanEqualAndEtat(superficierecherche,"disponible");
            if (bienData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(bienData, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/biens/recherchesuperficiemax/{superficierecherche}")
    public ResponseEntity<List<Bien>> findBySuperficieLessThanEqualAndEtat(@PathVariable("superficierecherche") Long superficierecherche){
        try {
            List<Bien> bienData = bienRepository.findBySuperficieLessThanEqualAndEtat(superficierecherche,"disponible");
            if (bienData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(bienData, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/biens/rechercheprixmin/{prixrecherche}")
    public ResponseEntity<List<Bien>> findByPrixGreaterThanEqualAndEtat(@PathVariable("prixrecherche") Long prixrecherche){
        try {
            List<Bien> bienData = bienRepository.findByPrixGreaterThanEqualAndEtat(prixrecherche,"disponible");
            if (bienData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(bienData, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/biens/rechercheprixmiax/{prixrecherche}")
    public ResponseEntity<List<Bien>> findByPrixLessThanEqualAndEtat(@PathVariable("prixrecherche") Long prixrecherche){
        try {
            List<Bien> bienData = bienRepository.findByPrixLessThanEqualAndEtat(prixrecherche,"disponible");
            if (bienData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {

                return new ResponseEntity<>(bienData, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}