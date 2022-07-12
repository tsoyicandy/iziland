
package com.itiad.iziland.models;

import com.itiad.iziland.models.entities.Utilisateur;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class MyUserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private static final long serialVersionUID = 1L;
    private String usermail;
    private String nom;
    private String prenom;
    @JsonIgnore
    private String motdepasse;
    private Long id;
    private Collection<? extends GrantedAuthority> authorities;


    public MyUserDetails(Long id, String nom,String prenom, String email, String password, Collection<? extends GrantedAuthority> authorities ){
        this.usermail=email;
        this.motdepasse=password;
        this.id=id;
        this.authorities = authorities;
        this.nom = nom;
        this.prenom = prenom;
    }

    public static MyUserDetails build(Utilisateur user) {
        List<GrantedAuthority> authorities = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new MyUserDetails(
                user.getId(),
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getMotdepasse(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {
        return motdepasse;
    }

    @Override
    public String getUsername() {
        return usermail;
    }

    public Long getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPreom() {
        return prenom;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MyUserDetails user = (MyUserDetails) o;
        return Objects.equals(id, user.id);
    }
}

