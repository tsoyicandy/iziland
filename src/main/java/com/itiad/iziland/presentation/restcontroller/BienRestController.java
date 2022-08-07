package com.itiad.iziland.presentation.restcontroller;


import com.itiad.iziland.models.entities.Bien;
import com.itiad.iziland.models.entities.FileInfo;
import com.itiad.iziland.models.entities.Proprietaire;
import com.itiad.iziland.repositories.BienRepository;
import com.itiad.iziland.repositories.FileInfoRepository;
import com.itiad.iziland.repositories.ProprietaireRepository;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import com.itiad.iziland.util.BienImage;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class BienRestController {

    @Autowired
    private BienRepository bienRepository;
    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private ProprietaireRepository proprietaireRepository;

    private Proprietaire getProprietaireById(Long id){
        return proprietaireRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!")));
    }

    private List<String> getFileInfoByBien(Bien bien)  {
        try {
            List<FileInfo> fileInfos = fileInfoRepository.findByBien(bien);
            List<String> urls = new ArrayList<String>();
            for(int i = 0; i<fileInfos.size(); i++){
                urls.add(i,fileInfos.get(i).getName());
            }
            return urls;
        }catch (Exception e){
            List<String> list = new ArrayList<>();
            list.add(e.getMessage());
            return list;
        }
    }


    @GetMapping("/biens")
    public ResponseEntity<List<BienImage>> getAllBien() {

        try {
            List<BienImage> bienImages = new ArrayList<BienImage>();
            List<Bien> biens = new ArrayList<Bien>();
            biens = bienRepository.findByEtat("disponible");
            BienImage bienImage;
            for(int i = 0; i<biens.size(); i++){
                bienImage = new BienImage(biens.get(i),getFileInfoByBien(biens.get(i)));
                bienImages.add(i,bienImage);
            }
            if (bienImages.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(bienImages, HttpStatus.OK);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/bienImages/{id}")
    public ResponseEntity<BienImage> getBienByIdPlusImages(@PathVariable("id") Long id){
        try {
            BienImage bienImage = new BienImage();
            Bien bien = bienRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!"))) ;
            bienImage.setBien(bien);
            bienImage.setListImage(getFileInfoByBien(bien));
            return ResponseEntity.ok(bienImage);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/biens")
    @PreAuthorize(" hasRole('GESTIONNAIRE') or hasRole('ADMIN')")
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
    @PreAuthorize(" hasRole('GESTIONNAIRE') or hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize(" hasRole('ADMIN')")
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

    @GetMapping("/biens/proprio/{idproprio}")
    @PreAuthorize(" hasRole('ADMIN')")
    public ResponseEntity<List<Bien>> getBiensByProprietaire(@PathVariable("idproprio") Long idproprio){
        try {
            List<Bien> bienData = bienRepository.findByProprietaire(getProprietaireById(idproprio));
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