
package com.covoiturage.service;

import com.covoiturage.dto.passager.PassagerRequest;
import com.covoiturage.dto.passager.PassagerResponse;

import java.util.List;
import java.util.UUID;

public interface PassagerService {

    PassagerResponse create(PassagerRequest request);

    List<PassagerResponse> findAll();

    PassagerResponse findById(UUID id);

    PassagerResponse update(UUID id, PassagerRequest request);

    void delete(UUID id);
}
