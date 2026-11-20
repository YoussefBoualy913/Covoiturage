
package com.covoiturage.controller;

import com.covoiturage.dto.reservation.ReservationRequest;
import com.covoiturage.dto.reservation.ReservationResponse;
import com.covoiturage.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @Valid @RequestBody ReservationRequest request) {

        ReservationResponse response = reservationService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findById(
            @PathVariable UUID id) {

        ReservationResponse response = reservationService.findById(id);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/passager/{passagerId}")
    public ResponseEntity<List<ReservationResponse>> findMyReservations(
            @PathVariable UUID passagerId) {

        List<ReservationResponse> response =
                reservationService.findMyReservations(passagerId);

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{reservationId}/annuler")
    public ResponseEntity<ReservationResponse> cancel(
            @PathVariable UUID reservationId,
            @RequestParam UUID passagerId) {

        ReservationResponse response =
                reservationService.cancel(reservationId, passagerId);

        return ResponseEntity.ok(response);
    }
}
