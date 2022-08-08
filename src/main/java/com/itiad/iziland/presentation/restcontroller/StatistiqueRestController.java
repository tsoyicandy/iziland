package com.itiad.iziland.presentation.restcontroller;


import com.itiad.iziland.models.Statistique;
import com.itiad.iziland.services.Iservices.StatisqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize(" hasRole('ADMIN')")
    public ResponseEntity<Statistique> getAllStatistiques(){
        Statistique statistique = new Statistique();
           statistique.setNombreTransactionEnCours(statisqueService.getnombreTransactionEnCours());
           statistique.setNombreUtilisateursApplication(statisqueService.getnombreUtilisateursApplication());
           statistique.setNombreBiensDisponible(statisqueService.getnombreBiensDisponible());
           statistique.setNombreBiensVendu(statisqueService.getnombreBiensVendu());
           statistique.setNombreTransactionTerminees(statisqueService.getnombreTransactionTerminees());
           statistique.setNombreProprietaire(statisqueService.getnombreProprietaire());
           statistique.setNombreTerrain(statisqueService.getnombreTerrainDisponible());
           statistique.setNombreTransactionAnnulee(statisqueService.getnombreTransactionAnnulee());
           statistique.setNombreProcurationGeneree(statisqueService.getnombreProcurationGeneree());
        return ResponseEntity.ok(statistique);

    }

}
