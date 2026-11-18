package com.covoiturage.service;

import com.covoiturage.dto.conducteur.ConducteurRequest;
import com.covoiturage.dto.conducteur.ConducteurResponse;

import java.util.List;
import java.util.UUID;

public interface ConducteurService {

    ConducteurResponse create(ConducteurRequest request);

    List<ConducteurResponse> findAll();

    ConducteurResponse findById(UUID id);

    ConducteurResponse update(UUID id, ConducteurRequest request);

    void delete(UUID id);

}
