package com.covoiturage.dto.trajet;


import com.covoiturage.entity.enums.StatutTrajet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TrajetResponse(
        UUID id,
        UUID conducteurId,
        UUID vehiculeId,
        String villeDepart,
        String villeArrivee,
        LocalDateTime dateDepart,
        int placesProposees,
        int placesRestantes,
        StatutTrajet statut,
        List<PointArretResponse> pointsArret
) {
}
