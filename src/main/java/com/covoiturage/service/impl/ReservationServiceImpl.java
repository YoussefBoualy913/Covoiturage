
package com.covoiturage.service.impl;

import com.covoiturage.dto.reservation.ReservationRequest;
import com.covoiturage.dto.reservation.ReservationResponse;
import com.covoiturage.entity.Passager;
import com.covoiturage.entity.PointArret;
import com.covoiturage.entity.Reservation;
import com.covoiturage.entity.Trajet;
import com.covoiturage.entity.enums.StatutReservation;
import com.covoiturage.entity.enums.StatutTrajet;
import com.covoiturage.exception.BusinessRuleException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.ReservationMapper;
import com.covoiturage.repository.PassagerRepository;
import com.covoiturage.repository.PointArretRepository;
import com.covoiturage.repository.ReservationRepository;
import com.covoiturage.repository.TrajetRepository;
import com.covoiturage.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final PassagerRepository passagerRepository;
    private final TrajetRepository trajetRepository;
    private final PointArretRepository pointArretRepository;
    private final ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ReservationResponse create(ReservationRequest request) {


        Passager passager = passagerRepository.findById(request.passagerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Passager introuvable avec l'id : " + request.passagerId()
                ));


        Trajet trajet = trajetRepository.findByIdForUpdate(request.trajetId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trajet introuvable avec l'id : " + request.trajetId()
                ));


        if (trajet.getStatut() != StatutTrajet.PUBLIE) {
            throw new BusinessRuleException(
                    "Impossible de réserver : le trajet n'est pas publié."
            );
        }


        PointArret pointMontee = pointArretRepository.findById(
                request.pointMonteeId()
        ).orElseThrow(() -> new ResourceNotFoundException(
                "Point de montée introuvable."
        ));

        PointArret pointDescente = pointArretRepository.findById(
                request.pointDescenteId()
        ).orElseThrow(() -> new ResourceNotFoundException(
                "Point de descente introuvable."
        ));


        if (!pointMontee.getTrajet().getId().equals(trajet.getId())
                || !pointDescente.getTrajet().getId().equals(trajet.getId())) {

            throw new BusinessRuleException(
                    "Les points de montée et de descente doivent appartenir au trajet."
            );
        }


        if (pointMontee.getOrdre() >= pointDescente.getOrdre()) {
            throw new BusinessRuleException(
                    "Le point de montée doit être avant le point de descente."
            );
        }


        int placesRestantes = calculerPlacesRestantes(trajet);

        if ( request.nombrePlaces() > placesRestantes) {
            throw new BusinessRuleException(
                    "Nombre de places insuffisant. Places restantes : "
                            + placesRestantes
            );
        }

        // 9. Vérifier une réservation confirmée par trajet et passager
        boolean existeReservation = reservationRepository
                .existsByPassagerIdAndTrajetIdAndStatut(
                        passager.getId(),
                        trajet.getId(),
                        StatutReservation.CONFIRMEE
                );

        if (existeReservation) {
            throw new BusinessRuleException(
                    "Vous avez déjà une réservation confirmée pour ce trajet."
            );
        }


        BigDecimal prixSegment = pointDescente.getPrixDepuisDepart()
                .subtract(pointMontee.getPrixDepuisDepart());

        BigDecimal montantTotal = prixSegment.multiply(
                BigDecimal.valueOf(request.nombrePlaces())
        );


        Reservation reservation = reservationMapper.toEntity(request);

        reservation.setPassager(passager);
        reservation.setTrajet(trajet);
        reservation.setPointMontee(pointMontee);
        reservation.setPointDescente(pointDescente);
        reservation.setDateReservation(LocalDateTime.now());
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setMontantTotal(montantTotal);

        Reservation savedReservation = reservationRepository.save(reservation);


        updateStatutTrajet(trajet);

        return reservationMapper.toResponse(savedReservation);
    }

    @Override
    public ReservationResponse findById(UUID id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Réservation introuvable avec l'id : " + id
                ));

        return reservationMapper.toResponse(reservation);
    }

    @Override
    public List<ReservationResponse> findMyReservations(UUID passagerId) {

        if (!passagerRepository.existsById(passagerId)) {
            throw new ResourceNotFoundException(
                    "Passager introuvable avec l'id : " + passagerId
            );
        }

        List<Reservation> reservations =
                reservationRepository.findByPassagerId(passagerId);

        return reservationMapper.toResponseList(reservations);
    }

    @Override
    @Transactional
    public ReservationResponse cancel(UUID reservationId, UUID passagerId) {


        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Réservation introuvable avec l'id : " + reservationId
                ));


        if (!reservation.getPassager().getId().equals(passagerId)) {
            throw new BusinessRuleException(
                    "Vous ne pouvez pas annuler la réservation d'un autre passager."
            );
        }


        if (reservation.getStatut() != StatutReservation.CONFIRMEE) {
            throw new BusinessRuleException(
                    "Seules les réservations confirmées peuvent être annulées."
            );
        }


        LocalDateTime dateDepart = reservation.getPointMontee()
                .getHeurePassage();

        if (LocalDateTime.now().isAfter(dateDepart.minusHours(24))) {
            throw new BusinessRuleException(
                    "L'annulation est impossible moins de 24 heures avant le départ."
            );
        }

        reservation.setStatut(StatutReservation.ANNULEE);

        Reservation savedReservation = reservationRepository.save(reservation);


        updateStatutTrajet(reservation.getTrajet());

        return reservationMapper.toResponse(savedReservation);
    }


    private int calculerPlacesRestantes(Trajet trajet) {

        long placesReservees = reservationRepository.countPlacesReservees(
                trajet.getId(),
                StatutReservation.CONFIRMEE
        );

        return trajet.getPlacesProposees() - (int) placesReservees;
    }


    private void updateStatutTrajet(Trajet trajet) {

        int placesRestantes = calculerPlacesRestantes(trajet);

        if (placesRestantes == 0) {
            trajet.setStatut(StatutTrajet.COMPLET);
        } else {
            trajet.setStatut(StatutTrajet.PUBLIE);
        }

        trajetRepository.save(trajet);
    }
}
