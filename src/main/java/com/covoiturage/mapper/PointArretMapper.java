package com.covoiturage.mapper;

import com.covoiturage.dto.trajet.PointArretRequest;
import com.covoiturage.dto.trajet.PointArretResponse;
import com.covoiturage.entity.PointArret;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointArretMapper {

    PointArretResponse toResponse(PointArret pointArret);

    PointArret toEntity(PointArretRequest request);
}
