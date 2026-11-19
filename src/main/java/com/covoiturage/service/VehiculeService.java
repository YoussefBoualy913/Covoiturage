package com.covoiturage.service;

import com.covoiturage.dto.vehicule.VehiculeRequest;
import com.covoiturage.dto.vehicule.VehiculeResponse;

import java.util.List;
import java.util.UUID;

public interface VehiculeService {

    VehiculeResponse create(VehiculeRequest request);

    VehiculeResponse update(UUID id, VehiculeRequest request);

    VehiculeResponse findById(UUID id);

    List<VehiculeResponse> findByConducteur(UUID conducteurId);

    void delete(UUID id);
}
