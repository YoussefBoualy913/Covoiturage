package com.covoiturage.dto.vehicule;

import java.util.UUID;

public record VehiculeResponse(
        UUID id,
        String marque,
        String modele,
        String immatriculation,
        String couleur,
        int capacite,
        String nomConducteur,
        String prenomConducteur
) {
}
