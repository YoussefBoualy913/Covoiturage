package com.covoiturage.repository;

import com.covoiturage.entity.Reservation;
import com.covoiturage.entity.enums.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByPassagerId(UUID passagerId);

    List<Reservation> findByTrajetId(UUID trajetId);

    boolean existsByPassagerIdAndTrajetIdAndStatut(
            UUID passagerId,
            UUID trajetId,
            StatutReservation statut
    );


    @Query("""
        SELECT COALESCE(SUM(r.nombrePlaces), 0)
        FROM Reservation r
        WHERE r.trajet.id = :trajetId
        AND r.statut = :statut
    """)
    long countPlacesReservees(
            @Param("trajetId") UUID trajetId,
            @Param("statut") StatutReservation statut
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
    UPDATE Reservation r
    SET r.statut = :nouveauStatut
    WHERE r.trajet.id = :trajetId
    AND r.statut = :ancienStatut
""")
    void annulerReservations(
            @Param("trajetId") UUID trajetId,
            @Param("ancienStatut") StatutReservation ancienStatut,
            @Param("nouveauStatut") StatutReservation nouveauStatut
    );

}
