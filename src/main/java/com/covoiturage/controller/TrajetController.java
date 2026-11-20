package com.covoiturage.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.covoiturage.dto.reservation.ReservationResponse;
import com.covoiturage.dto.trajet.TrajetRequest;
import com.covoiturage.dto.trajet.TrajetResponse;
import com.covoiturage.service.TrajetService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/trajets")
@RequiredArgsConstructor
public class TrajetController {

    private final TrajetService trajetService;


    @PostMapping
    public ResponseEntity<TrajetResponse> create(
            @Valid @RequestBody TrajetRequest request) {

        TrajetResponse response = trajetService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/conducteur/{conducteurId}")
    public ResponseEntity<List<TrajetResponse>> findByConducteur(@PathVariable UUID conducteurId) {

        return ResponseEntity.ok(trajetService.findByConducteur(conducteurId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TrajetResponse> findById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(trajetService.findById(id));
    }

//    @GetMapping("/recherche")
//    public ResponseEntity<Page<TrajetResponse>> rechercher(
//            @RequestParam String villeDepart,
//            @RequestParam String villeArrivee,
//            @RequestParam
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//            LocalDate date,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        return ResponseEntity.ok(
//                trajetService.rechercher(
//                        villeDepart, villeArrivee, date, page, size
//                )
//        );
//    }
//
//
//    @GetMapping("/{id}/reservations")
//    public ResponseEntity<List<ReservationResponse>> findReservations(
//            @PathVariable UUID id) {
//
//        return ResponseEntity.ok(
//                trajetService.findReservations(id)
//        );
//    }
//
//
    @PatchMapping("/{id}/terminer")
    public ResponseEntity<Void> terminer(
            @PathVariable UUID id) {

        trajetService.terminer(id);

        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}/annuler")
    public ResponseEntity<Void> annuler(
            @PathVariable UUID id) {

        trajetService.annuler(id);

        return ResponseEntity.noContent().build();
    }

}
