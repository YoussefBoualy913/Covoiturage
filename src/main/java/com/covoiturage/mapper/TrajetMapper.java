package com.covoiturage.mapper;

import com.covoiturage.dto.trajet.TrajetRechercheResponse;
import com.covoiturage.dto.trajet.TrajetRequest;
import com.covoiturage.dto.trajet.TrajetResponse;
import com.covoiturage.entity.Trajet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = PointArretMapper.class)
public interface TrajetMapper {

    @Mapping(source = "conducteur.id", target = "conducteurId")
    @Mapping(source = "vehicule.id", target = "vehiculeId")
    @Mapping(target = "villeDepart",
            expression = "java(trajet.getPointsArret().get(0).getVille())")
    @Mapping(target = "villeArrivee",
            expression = "java(trajet.getPointsArret().get(trajet.getPointsArret().size() - 1).getVille())")
    @Mapping(target = "placesRestantes", ignore = true)
    TrajetResponse toResponse(Trajet trajet);

    @Mapping(source = "conducteur.nom", target = "nomConducteur")
    @Mapping(target = "villeDepart",
            expression = "java(trajet.getPointsArret().get(0).getVille())")
    @Mapping(target = "villeArrivee",
            expression = "java(trajet.getPointsArret().get(trajet.getPointsArret().size() - 1).getVille())")
    @Mapping(target = "placesRestantes", ignore = true)
    TrajetRechercheResponse toRechercheResponse(Trajet trajet);

    List<TrajetResponse> toResponseList(List<Trajet> trajets);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "conducteur", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "dateDepart", ignore = true)
    @Mapping(target = "pointsArret", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Trajet toEntity(TrajetRequest request);
}
