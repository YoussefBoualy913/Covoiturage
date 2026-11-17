package com.covoiturage.repository;

import com.covoiturage.entity.Passager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PassagerRepository extends JpaRepository<Passager, UUID> {

    Optional<Passager> findByEmail(String email);
}
