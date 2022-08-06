package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.AppUtilisateurRole;
import com.itiad.iziland.models.MyUserDetails;
import com.itiad.iziland.models.entities.Role;
import com.itiad.iziland.models.entities.Utilisateur;
import com.itiad.iziland.payloads.request.LoginRequest;
import com.itiad.iziland.payloads.request.SignupRequest;
import com.itiad.iziland.payloads.response.JwtResponse;
import com.itiad.iziland.payloads.response.MessageResponse;
import com.itiad.iziland.repositories.RoleRepository;
import com.itiad.iziland.repositories.UtilisateurRepository;
import com.itiad.iziland.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class HomeRestController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UtilisateurRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtils;

    private Utilisateur getAGestionnaireByName(){
        Role role = roleRepository.findByName(AppUtilisateurRole.GESTIONNAIRE);
        Utilisateur managers =  userRepository.findByRole(role);
        return managers;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getMotdepasse()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    roles));
        }catch (Exception e){
            return new ResponseEntity(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByNom(signUpRequest.getNom())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Name is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        Utilisateur user;
        user = new Utilisateur(signUpRequest.getNom(),signUpRequest.getPrenom(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getMotdepasse()),
                signUpRequest.getTelephone());

        Set<String> strRoles =  signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Set<Utilisateur> gestionnaire = new HashSet<>();
        gestionnaire.add(getAGestionnaireByName());

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(AppUtilisateurRole.USER);
            user.setGestionnaire(gestionnaire);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(AppUtilisateurRole.ADMIN);
                        roles.add(adminRole);
                        break;
                    case "ges":
                        Role gesRole = roleRepository.findByName(AppUtilisateurRole.GESTIONNAIRE);
                        roles.add(gesRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(AppUtilisateurRole.USER);
                        user.setGestionnaire(gestionnaire);
                        roles.add(userRole);
                }
            });
        }
        user.setRole(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}




