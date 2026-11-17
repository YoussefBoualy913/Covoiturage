package com.covoiturage.repository;

import com.covoiturage.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByPassagerId(UUID passagerId);

    List<Reservation> findByTrajetId(UUID trajetId);

}
