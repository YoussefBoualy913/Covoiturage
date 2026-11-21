
package com.covoiturage.dto.trajet;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RechercheTrajetRequest(

        @NotBlank(message = "La ville de départ est obligatoire")
        @Size(max = 100, message = "La ville de départ ne doit pas dépasser 100 caractères")
        String villeDepart,

        @NotBlank(message = "La ville d'arrivée est obligatoire")
        @Size(max = 100, message = "La ville d'arrivée ne doit pas dépasser 100 caractères")
        String villeArrivee,

        @NotNull(message = "La date est obligatoire")
        @FutureOrPresent(message = "La date doit être aujourd'hui ou dans le futur")
        LocalDate date,

        @NotNull(message = "Le nombre de places est obligatoire")
        @Min(value = 1, message = "Le nombre de places doit être au minimum 1")
        Integer nombrePlaces,

        @DecimalMin(value = "0.0", message = "Le prix maximum doit être positif ou nul")
        BigDecimal prixMax
) {
}
