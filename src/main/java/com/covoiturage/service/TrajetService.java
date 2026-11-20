package com.covoiturage.service;

import com.covoiturage.dto.reservation.ReservationResponse;
import com.covoiturage.dto.trajet.RechercheTrajetRequest;
import com.covoiturage.dto.trajet.TrajetRechercheResponse;
import com.covoiturage.dto.trajet.TrajetRequest;
import com.covoiturage.dto.trajet.TrajetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TrajetService {

    TrajetResponse create(TrajetRequest request);

    TrajetResponse findById(UUID id);

//
    List<TrajetResponse> findByConducteur(UUID conducteurId);
//
//    List<ReservationResponse> findReservations(UUID conducteurId, UUID trajetId);
//
//    Page<TrajetRechercheResponse> rechercher(RechercheTrajetRequest criteres, Pageable pageable);
//
    void terminer(UUID trajetId);

    void annuler( UUID trajetId);
}
