
package com.covoiturage.service.impl;

import com.covoiturage.dto.vehicule.VehiculeRequest;
import com.covoiturage.dto.vehicule.VehiculeResponse;
import com.covoiturage.entity.Conducteur;
import com.covoiturage.entity.Vehicule;
import com.covoiturage.exception.*;
import com.covoiturage.mapper.VehiculeMapper;
import com.covoiturage.repository.ConducteurRepository;
import com.covoiturage.repository.VehiculeRepository;
import com.covoiturage.service.VehiculeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;
    private final ConducteurRepository conducteurRepository;

    @Override
    @Transactional
    public VehiculeResponse create(VehiculeRequest request) {

        if (vehiculeRepository.existsByImmatriculation(request.immatriculation())) {
            throw new DuplicateResourceException(
                    "Cette immatriculation est déjà utilisée."
            );
        }

        Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Conducteur introuvable avec l'id : " + request.conducteurId()
                ));

        Vehicule vehicule = vehiculeMapper.toEntity(request);

        if (vehicule.getCapacite() < 1 || vehicule.getCapacite() > 8) {
            throw new BusinessRuleException(
                    "La capacité du véhicule doit être comprise entre 1 et 8 places."
            );
        }

        vehicule.setConducteur(conducteur);
        Vehicule savedVehicule = vehiculeRepository.save(vehicule);

        return vehiculeMapper.toResponse(savedVehicule);
    }

    @Override
    @Transactional
    public VehiculeResponse update(UUID id, VehiculeRequest request) {

        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Véhicule introuvable avec l'id : " + id
                ));

        if (vehiculeRepository.existsByImmatriculationAndIdNot(
                request.immatriculation(), id)) {
            throw new DuplicateResourceException(
                    "Cette immatriculation est déjà utilisée."
            );
        }

        if (request.capacite() < 1 || request.capacite() > 8) {
            throw new BusinessRuleException(
                    "La capacité du véhicule doit être comprise entre 1 et 8 places."
            );
        }

        vehiculeMapper.updateFromRequest(request, vehicule);

        Vehicule updatedVehicule = vehiculeRepository.save(vehicule);

        return vehiculeMapper.toResponse(updatedVehicule);
    }

    @Override
    public VehiculeResponse findById(UUID id) {

        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Véhicule introuvable avec l'id : " + id
                ));

        return vehiculeMapper.toResponse(vehicule);
    }

    @Override
    public List<VehiculeResponse> findByConducteur(UUID conducteurId) {

        List<Vehicule> vehicules =
                vehiculeRepository.findByConducteurId(conducteurId);

        return vehiculeMapper.toResponseList(vehicules);
    }

    @Override
    @Transactional
    public void delete(UUID id) {

        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Véhicule introuvable avec l'id : " + id
                ));

        vehiculeRepository.delete(vehicule);
    }
}
