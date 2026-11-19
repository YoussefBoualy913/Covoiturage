package com.covoiturage.dto.trajet;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record TrajetRequest(
        @NotNull UUID vehiculeId,
        @NotNull UUID conducteurId,
        @NotNull @Min(1) Integer placesProposees,
        @NotEmpty List<@Valid PointArretRequest> pointsArret
) {
}
