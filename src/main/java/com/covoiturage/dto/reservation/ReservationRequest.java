package com.covoiturage.dto.reservation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationRequest(
        @NotNull UUID pointMonteeId,
        @NotNull UUID pointDescenteId,
        @NotNull @Min(1) Integer nombrePlaces,
        @NotNull  UUID passagerId,
        @NotNull  UUID trajetId

) {
}
