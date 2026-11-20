package com.covoiturage.repository;

import com.covoiturage.entity.Trajet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrajetRepository extends JpaRepository<Trajet, UUID> {

    List<Trajet> findByConducteurId(UUID conducteurId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Trajet t WHERE t.id = :id")
    Optional<Trajet> findByIdForUpdate(@Param("id") UUID id);

}
