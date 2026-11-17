package com.covoiturage.mapper;

import com.covoiturage.dto.trajet.TrajetResponse;
import com.covoiturage.entity.Trajet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = PointArretMapper.class)
public interface TrajetMapper {

    @Mapping(source = "conducteur.id", target = "conducteurId")
    @Mapping(source = "vehicule.id", target = "vehiculeId")
    @Mapping(target = "placesRestantes", ignore = true)
    TrajetResponse toResponse(Trajet trajet);

    List<TrajetResponse> toResponseList(List<Trajet> trajets);
}
