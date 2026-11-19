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


    @GetMapping
    public ResponseEntity<List<TrajetResponse>> findByConducteur(@PathVariable UUID conducteurId) {

        return ResponseEntity.ok(trajetService.findByConducteur(conducteurId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TrajetResponse> findById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(trajetService.findById(id));
    }



}
