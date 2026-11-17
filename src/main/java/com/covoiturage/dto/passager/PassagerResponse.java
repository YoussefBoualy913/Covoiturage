package com.covoiturage.dto.passager;

import java.util.UUID;

public record PassagerResponse(
        UUID id,
        String nom,
        String prenom,
        String email,
        String telephone,
        boolean isActive
) {
}
