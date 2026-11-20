package com.covoiturage.service.impl;
import com.covoiturage.dto.trajet.*;
import com.covoiturage.entity.*;
import com.covoiturage.entity.enums.StatutReservation;
import com.covoiturage.entity.enums.StatutTrajet;

import com.covoiturage.exception.AccessDeniedException;
import com.covoiturage.exception.BusinessRuleException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.TrajetMapper;
import com.covoiturage.repository.ReservationRepository;
import com.covoiturage.repository.TrajetRepository;
import com.covoiturage.repository.VehiculeRepository;

import com.covoiturage.service.TrajetService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class TrajetServiceImpl implements TrajetService {

    private final TrajetRepository trajetRepository;
    private final VehiculeRepository vehiculeRepository;
    private final TrajetMapper trajetMapper;
    private final ReservationRepository reservationRepository;

    @Override
    public TrajetResponse create(TrajetRequest request) {


        Vehicule vehicule = vehiculeRepository.findById(request.vehiculeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Véhicule introuvable"));


        if (!vehicule.getConducteur().getId().equals(request.conducteurId())) {
            throw new AccessDeniedException(
                    "Vous ne pouvez utiliser que vos propres véhicules"
            );
        }


        if (request.placesProposees() > vehicule.getCapacite()) {
            throw new BusinessRuleException(
                    "Le nombre de places proposées dépasse la capacité du véhicule"
            );
        }


        List<PointArretRequest> points = request.pointsArret()
                .stream()
                .sorted(Comparator.comparingInt(PointArretRequest::ordre))
                .toList();

        validerPointsArret(points);


        Trajet trajet = Trajet.builder()
                .dateDepart(points.get(0).heurePassage())
                .placesProposees(request.placesProposees())
                .statut(StatutTrajet.PUBLIE)
                .conducteur(vehicule.getConducteur())
                .vehicule(vehicule)
                .build();


        List<PointArret> pointsArret = points.stream()
                .map(point -> PointArret.builder()
                        .ville(point.ville().trim())
                        .ordre(point.ordre())
                        .heurePassage(point.heurePassage())
                        .prixDepuisDepart(point.prixDepuisDepart())
                        .trajet(trajet)
                        .build())
                .toList();

        trajet.setPointsArret(new ArrayList<>(pointsArret));


        Trajet savedTrajet = trajetRepository.save(trajet);

        return trajetMapper.toResponse(savedTrajet);
    }

    @Override
    public List<TrajetResponse> findByConducteur(UUID conducteurId) {

        List<Trajet> trajets = trajetRepository.findByConducteurId(conducteurId);

        return trajetMapper.toResponseList(trajets);
    }

    @Override
    public TrajetResponse findById(UUID id) {

        Trajet trajet = trajetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trajet introuvable avec l'id : " + id
                ));

        return trajetMapper.toResponse(trajet);
    }

    @Override
    @Transactional
    public void terminer(UUID id) {

        Trajet trajet = trajetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trajet introuvable avec l'id : " + id
                ));

        if (trajet.getStatut() != StatutTrajet.PUBLIE
                && trajet.getStatut() != StatutTrajet.COMPLET) {
            throw new BusinessRuleException(
                    "Impossible de terminer ce trajet avec son statut actuel."
            );
        }

        if (trajet.getDateDepart().isAfter(LocalDateTime.now())) {
            throw new BusinessRuleException(
                    "Impossible de terminer un trajet avant son départ."
            );
        }

        trajet.setStatut(StatutTrajet.TERMINE);
    }

    @Override
    @Transactional
    public void annuler(UUID id) {

        Trajet trajet = trajetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trajet introuvable avec l'id : " + id
                ));

        if (trajet.getStatut() == StatutTrajet.ANNULE) {
            throw new BusinessRuleException("Ce trajet est déjà annulé.");
        }


        reservationRepository.annulerReservations(
                trajet.getId(),
                StatutReservation.CONFIRMEE,
                StatutReservation.ANNULEE
        );


        trajet.setStatut(StatutTrajet.ANNULE);

        trajetRepository.save(trajet);
    }


    private void validerPointsArret(List<PointArretRequest> points) {


        if (points.size() < 2) {
            throw new BusinessRuleException(
                    "Un trajet doit contenir au moins deux points d'arrêt"
            );
        }

        LocalDateTime maintenant = LocalDateTime.now();


        if (!points.get(0).heurePassage().isAfter(maintenant)) {
            throw new BusinessRuleException(
                    "La date de départ doit être dans le futur"
            );
        }


        if (points.get(0).ordre() != 0) {
            throw new BusinessRuleException(
                    "Le premier point doit avoir l'ordre 0"
            );
        }


        if (points.get(0).prixDepuisDepart()
                .compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessRuleException(
                    "Le prix du point de départ doit être égal à 0"
            );
        }

        Set<String> villes = new HashSet<>();

        for (int i = 0; i < points.size(); i++) {

            PointArretRequest point = points.get(i);

            if (point.ordre() != i) {
                throw new BusinessRuleException(
                        "Les ordres des points doivent être successifs"
                );
            }


            if (!villes.add(point.ville().trim().toLowerCase())) {
                throw new BusinessRuleException(
                        "Une ville ne peut apparaître qu'une seule fois"
                );
            }

            if (i > 0) {

                PointArretRequest precedent = points.get(i - 1);


                if (!point.heurePassage().isAfter(precedent.heurePassage())) {
                    throw new BusinessRuleException(
                            "Les heures de passage doivent être strictement croissantes"
                    );
                }


                if (point.prixDepuisDepart()
                        .compareTo(precedent.prixDepuisDepart()) <= 0) {
                    throw new BusinessRuleException(
                            "Les prix depuis le départ doivent être strictement croissants"
                    );
                }
            }
        }
    }
}
