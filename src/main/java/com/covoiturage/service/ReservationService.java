
package com.covoiturage.service;

import com.covoiturage.dto.reservation.ReservationRequest;
import com.covoiturage.dto.reservation.ReservationResponse;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    ReservationResponse create(ReservationRequest request);

    ReservationResponse findById(UUID id);

    List<ReservationResponse> findMyReservations(UUID passagerId);

    ReservationResponse cancel(UUID reservationId, UUID passagerId);
}
