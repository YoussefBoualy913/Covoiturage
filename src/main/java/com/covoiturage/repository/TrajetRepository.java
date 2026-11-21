package com.covoiturage.repository;

import com.covoiturage.entity.Trajet;
import com.covoiturage.entity.enums.StatutReservation;
import com.covoiturage.entity.enums.StatutTrajet;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrajetRepository extends JpaRepository<Trajet, UUID> {

    List<Trajet> findByConducteurId(UUID conducteurId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Trajet t WHERE t.id = :id")
    Optional<Trajet> findByIdForUpdate(@Param("id") UUID id);

    @Query(
            value = """
            SELECT  t
            FROM Trajet t
            JOIN t.pointsArret depart
            JOIN t.pointsArret arrivee
            WHERE t.statut = :statut

            AND LOWER(depart.ville) = LOWER(:villeDepart)
            AND LOWER(arrivee.ville) = LOWER(:villeArrivee)

            AND depart.ordre < arrivee.ordre

            AND depart.heurePassage >= :debutJournee
            AND depart.heurePassage < :finJournee

            AND (
                :prixMax IS NULL
                OR (arrivee.prixDepuisDepart - depart.prixDepuisDepart) <= :prixMax
            )

            AND (
                t.placesProposees - COALESCE(
                    (
                        SELECT SUM(r.nombrePlaces)
                        FROM Reservation r
                        WHERE r.trajet.id = t.id
                        AND r.statut = :statutReservation
                    ),
                    0
                )
            ) >= :nombrePlaces

            ORDER BY depart.heurePassage ASC
            """,

            countQuery = """
            SELECT COUNT(DISTINCT t)
            FROM Trajet t
            JOIN t.pointsArret depart
            JOIN t.pointsArret arrivee
            WHERE t.statut = :statut

            AND LOWER(depart.ville) = LOWER(:villeDepart)
            AND LOWER(arrivee.ville) = LOWER(:villeArrivee)

            AND depart.ordre < arrivee.ordre

            AND depart.heurePassage >= :debutJournee
            AND depart.heurePassage < :finJournee

            AND (
                :prixMax IS NULL
                OR (arrivee.prixDepuisDepart - depart.prixDepuisDepart) <= :prixMax
            )

            AND (
                t.placesProposees - COALESCE(
                    (
                        SELECT SUM(r.nombrePlaces)
                        FROM Reservation r
                        WHERE r.trajet.id = t.id
                        AND r.statut = :statutReservation
                    ),
                    0
                )
            ) >= :nombrePlaces
            """
    )
    Page<Trajet> rechercher(
            @Param("villeDepart") String villeDepart,
            @Param("villeArrivee") String villeArrivee,
            @Param("debutJournee") LocalDateTime debutJournee,
            @Param("finJournee") LocalDateTime finJournee,
            @Param("nombrePlaces") Integer nombrePlaces,
            @Param("prixMax") BigDecimal prixMax,
            @Param("statut") StatutTrajet statut,
            @Param("statutReservation") StatutReservation statutReservation,
            Pageable pageable
    );

}
