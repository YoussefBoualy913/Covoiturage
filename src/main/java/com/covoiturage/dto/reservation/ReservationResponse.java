package com.covoiturage.dto.reservation;


import com.covoiturage.entity.enums.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationResponse(
        UUID id,
        UUID passagerId,
        UUID trajetId,
        UUID pointMonteeId,
        String villeMontee,
        UUID pointDescenteId,
        String villeDescente,
        int nombrePlaces,
        BigDecimal montantTotal,
        LocalDateTime dateReservation,
        StatutReservation statut
) {
}
