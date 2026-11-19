package com.covoiturage.repository;

import com.covoiturage.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VehiculeRepository extends JpaRepository<Vehicule, UUID> {

    List<Vehicule> findByConducteurId(UUID conducteurId);

    boolean existsByImmatriculation(String immatriculation);
    boolean existsByImmatriculationAndIdNot(String immatriculation,UUID id);
//    boolean existsByTrajetsVehiculeId(UUID trajetsVehiculeId);
}
