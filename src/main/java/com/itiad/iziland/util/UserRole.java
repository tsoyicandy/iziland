package com.itiad.iziland.util;


import com.itiad.iziland.models.entities.Role;
import com.itiad.iziland.models.entities.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRole {
    Utilisateur utilisateur;
    Role role;
}
