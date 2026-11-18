
package com.covoiturage.controller;

import com.covoiturage.dto.passager.PassagerRequest;
import com.covoiturage.dto.passager.PassagerResponse;
import com.covoiturage.service.PassagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/passagers")
@RequiredArgsConstructor
public class PassagerController {

    private final PassagerService passagerService;

    @PostMapping
    public ResponseEntity<PassagerResponse> create(
            @Valid @RequestBody PassagerRequest request) {

        PassagerResponse passagerResponse = passagerService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(passagerResponse);
    }

    @GetMapping
    public ResponseEntity<List<PassagerResponse>> findAll() {
        return ResponseEntity.ok(passagerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassagerResponse> findById(@PathVariable UUID id) {
        PassagerResponse passagerResponse = passagerService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(passagerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassagerResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody PassagerRequest request) {

        PassagerResponse response = passagerService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        passagerService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
