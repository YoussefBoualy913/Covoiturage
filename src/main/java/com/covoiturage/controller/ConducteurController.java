package com.covoiturage.controller;

import com.covoiturage.dto.conducteur.ConducteurRequest;
import com.covoiturage.dto.conducteur.ConducteurResponse;
import com.covoiturage.service.ConducteurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/conducteurs")
@RequiredArgsConstructor
public class ConducteurController {

    private final ConducteurService conducteurService;

    @PostMapping
    public ResponseEntity<ConducteurResponse> create(@Valid @RequestBody ConducteurRequest request) {
        ConducteurResponse conducteurResponse = conducteurService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(conducteurResponse);
    }

    @GetMapping
    public ResponseEntity<List<ConducteurResponse>> findAll() {
        return  ResponseEntity.ok(conducteurService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConducteurResponse> findById(@PathVariable UUID id) {
        ConducteurResponse conducteurResponse = conducteurService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(conducteurResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConducteurResponse> update(
            @PathVariable UUID id, @Valid @RequestBody ConducteurRequest request) {
        ConducteurResponse response = conducteurService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        conducteurService.delete(id);

        return ResponseEntity.noContent().build();
    }


}
