package com.covoiturage.dto.trajet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PointArretResponse(
        UUID id,
        String ville,
        int ordre,
        LocalDateTime heurePassage,
        BigDecimal prixDepuisDepart
) {
}
