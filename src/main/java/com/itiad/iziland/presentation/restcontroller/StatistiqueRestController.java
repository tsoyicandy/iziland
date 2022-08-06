package com.itiad.iziland.presentation.restcontroller;


import com.itiad.iziland.models.Statistique;
import com.itiad.iziland.services.Iservices.StatisqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class StatistiqueRestController {

    @Autowired
    private StatisqueService statisqueService;

    @GetMapping("/stats")
    public ResponseEntity<Statistique> getAllStatistiques(){
        Statistique statistique = new Statistique(
                statisqueService.getnombreTransactionEnCours(),
                statisqueService.getnombreUtilisateursApplication(),
                statisqueService.getnombreBiensDisponible(),
                statisqueService.getnombreBiensVendu(),
                statisqueService.getnombreTransactionTerminees(),
                statisqueService.getnombreProprietaire(),
                statisqueService.getnombreTerrain(),
                statisqueService.getnombreTransactionAnnulee(),
                statisqueService.getnombreProcurationGeneree()
        );
        return ResponseEntity.ok(statistique);

    }

}
