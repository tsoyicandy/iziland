package com.itiad.iziland.payloads.request;


import com.itiad.iziland.models.AppUtilisateurRole;
import com.itiad.iziland.models.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotBlank
    private String motdepasse;
    @NotBlank
    private String email;
    @NotBlank
    private String telephone;

    private Set<String> role;
}
