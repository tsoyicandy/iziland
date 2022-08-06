package com.itiad.iziland.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Statistique {
    private Long nombreTransactionEnCours;
    private Long nombreUtilisateursApplication;
    private Long nombreBiensDisponible;
    private Long nombreBiensVendu;
    private Long nombreTransactionTerminees;
    private Long nombreProprietaire;
    private Long nombreTerrain;
    private Long nombreTransactionAnnulee;
    private Long nombreProcurationGeneree;
}
