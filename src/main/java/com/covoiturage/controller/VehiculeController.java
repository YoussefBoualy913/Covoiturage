
package com.covoiturage.controller;

import com.covoiturage.dto.vehicule.VehiculeRequest;
import com.covoiturage.dto.vehicule.VehiculeResponse;
import com.covoiturage.service.VehiculeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @PostMapping
    public ResponseEntity<VehiculeResponse> create(
            @Valid @RequestBody VehiculeRequest request) {

        VehiculeResponse response = vehiculeService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculeResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody VehiculeRequest request) {

        VehiculeResponse response = vehiculeService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeResponse> findById(@PathVariable UUID id) {

        VehiculeResponse response = vehiculeService.findById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/conducteur/{conducteurId}")
    public ResponseEntity<List<VehiculeResponse>> findByConducteur(
            @PathVariable UUID conducteurId) {

        List<VehiculeResponse> response =
                vehiculeService.findByConducteur(conducteurId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        vehiculeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
