package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.Procuration;
import com.itiad.iziland.models.entities.Utilisateur;
import com.itiad.iziland.repositories.ProcurationRepository;
import com.itiad.iziland.repositories.UtilisateurRepository;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import com.itiad.iziland.services.Iservices.QRCodeService;
import com.itiad.iziland.util.RandomWordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class ProcurationRestController {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    @Autowired
    private RandomWordGenerator randomWordGenerator;

    @Autowired
    private ProcurationRepository procurationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private QRCodeService qrCodeService;



    @GetMapping("/qrcodes/{idprocuration}")
    @ResponseBody
    public ResponseEntity<Resource> downloadQrcode(@PathVariable("idprocuration") Long idprocuration){
        Optional<Procuration> procuration = procurationRepository.findById(idprocuration);
        String code = procuration.get().getQrcode();
        byte[] file = qrCodeService.generateQrCode(
                code
                        +"\n"+procuration.get().getMotif()
                        +"\n"+procuration.get().getNomBeneficiare()
                        +"\n"+procuration.get().getCni()
                        +"\n Procuration crée le "+procuration.get().getDateDeCreation()
                        +"\n par :  "+procuration.get().getUtilisateur().getNom()
                        +"\n "+procuration.get().getUtilisateur().getPrenom()
                ,WIDTH,HEIGHT);
        Resource resource = new ByteArrayResource(file);
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + procuration.get().getNomBeneficiare()+"procuration" +".PNG" + "\"")
                    .body(resource);
        } else {
            throw new RuntimeException("Could not read the file!");
        }


    }

    @PostMapping("/procurations/{idutilisateur}")
    public Procuration saveProcuration(@PathVariable("idutilisateur") Long idutilisateur, @RequestBody Procuration procuration){

        procuration.setUtilisateur(getUtilisateurById(idutilisateur));
        String code = randomWordGenerator.generateRandomWord();

        procuration.setQrcode(code);

        return procurationRepository.save(procuration);
    }

    @GetMapping("/procuration/{id}")
    public ResponseEntity<Procuration> getProceduralById(@PathVariable("id") Long id){
        Procuration procuration = procurationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!"))) ;
        return ResponseEntity.ok(procuration);
    }

    @DeleteMapping("/procurations/{idprocuration}")
    public ResponseEntity<Void> deleteProcuration(@PathVariable("idprocuration") Long idprocuration){

       if (procurationRepository.existsById(idprocuration)){
           procurationRepository.deleteById(idprocuration);
           return new ResponseEntity<>(HttpStatus.OK);
       }else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    private Utilisateur getUtilisateurById(Long idutilisateur) {
        return utilisateurRepository.findById(idutilisateur).orElseThrow(()-> new ResourceNotFoundException(("cet utilisateur n'existe pas !!")));


}

    @DeleteMapping("/procurations")
    public ResponseEntity<Void> deleteProcurations(){

            procurationRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/qrcodes/{idutilisateur}")
    public ResponseEntity<List<Procuration>> findByUtilisateur(@PathVariable("idutilisateur") Long idutilisateur){
        List procs = procurationRepository.findByUtilisateur(idutilisateur);
        if (!procs.isEmpty()){
            return new ResponseEntity<>(procs,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}

