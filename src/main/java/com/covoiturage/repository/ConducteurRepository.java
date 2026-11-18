package com.covoiturage.repository;

import com.covoiturage.entity.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConducteurRepository extends JpaRepository<Conducteur, UUID> {

    Optional<Conducteur> findByEmail(String email);

    Optional<Conducteur> findByNumeroPermis(String numeroPermis);
    boolean existsByNumeroPermis(String numeroPermis);
    boolean existsByNumeroPermisAndIdNot(String numeroPermis, UUID id);
}
