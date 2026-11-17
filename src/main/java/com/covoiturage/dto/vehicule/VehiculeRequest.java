package com.covoiturage.dto.vehicule;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VehiculeRequest(
        @NotBlank String marque,
        @NotBlank String modele,
        @NotBlank String immatriculation,
        String couleur,
        @NotNull @Min(1) @Max(8) Integer capacite,
        @NotNull UUID conducteurId
) {
}
