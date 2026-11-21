package com.covoiturage.dto.trajet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public record TrajetRechercheResponse(
        UUID trajetId,
        String villeDepart,
        String villeArrivee,
        LocalDateTime heureDepart,
        BigDecimal prixSegment,
        int placesRestantes,
        String nomConducteur
) {
}
