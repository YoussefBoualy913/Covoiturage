package com.covoiturage.repository;

import com.covoiturage.entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrajetRepository extends JpaRepository<Trajet, Long> {

    List<Trajet> findByConducteurId(UUID conducteurId);

}
