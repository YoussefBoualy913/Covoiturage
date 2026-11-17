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
    public ResponseEntity<ConducteurResponse> findById(@PathVariable Long id) {
        throw new UnsupportedOperationException("");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConducteurResponse> update(
            @PathVariable Long id, @Valid @RequestBody ConducteurRequest request) {
        throw new UnsupportedOperationException("");
    }

    @GetMapping("/search")
    public ResponseEntity<List<ConducteurResponse>> search(@RequestParam String query) {
        throw new UnsupportedOperationException("");
    }
}
