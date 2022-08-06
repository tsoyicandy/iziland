package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.Role;
import com.itiad.iziland.models.entities.Utilisateur;
import com.itiad.iziland.repositories.RoleRepository;
import com.itiad.iziland.repositories.UtilisateurRepository;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import com.itiad.iziland.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class UtilisateurRestController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/utilisateurs")
    public List<UserRole> getAllUtilisateur() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        List<UserRole> userRoles =null;
        UserRole userRole = null;
        for (Utilisateur utilisateur : utilisateurs) {
            assert false;
            userRole.setUtilisateur(utilisateur);
            userRole.setRole((Role) utilisateur.getRole());
        }
        return userRoles;
    }

    @GetMapping("/utilisateurs/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id){
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cet Utilisateur n'existe pas !!"))) ;
        return ResponseEntity.ok(utilisateur);
    }

    @PutMapping("/utilisateurs/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateur){
        Utilisateur utilisateurexistant = utilisateurRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cet Utilisateur n'existe pas !!"))) ;
        utilisateurexistant.setId(id);
        utilisateurexistant.setEmail(utilisateur.getEmail());
        utilisateurexistant.setNom(utilisateur.getNom());
        utilisateurexistant.setTelephone (utilisateur.getTelephone());
        utilisateurexistant.setMotdepasse(utilisateur.getMotdepasse());
        utilisateurexistant.setRole(utilisateur.getRole());
        Utilisateur utilisateurupdated = utilisateurRepository.save(utilisateurexistant);

        return ResponseEntity.ok(utilisateurupdated);
    }


    @DeleteMapping("/utilisateurs/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUtilisateur(@PathVariable Long id){
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cet Utilisateur n'existe pas !!"))) ;
        utilisateurRepository.delete(utilisateur);
        Map<String, Boolean> reponse = new HashMap<>();
        reponse.put("supprime", Boolean.TRUE);
        return ResponseEntity.ok(reponse);
    }

    @PostMapping("/utilisateurs")
    public Utilisateur saveUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }
}
