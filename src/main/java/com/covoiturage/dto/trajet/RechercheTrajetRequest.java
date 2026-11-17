package com.covoiturage.dto.trajet;

import java.math.BigDecimal;
import java.time.LocalDate;


public record RechercheTrajetRequest(
        String villeDepart,
        String villeArrivee,
        LocalDate date,
        BigDecimal prixMax
) {
}
