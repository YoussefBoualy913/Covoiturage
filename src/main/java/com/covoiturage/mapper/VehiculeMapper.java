package com.covoiturage.mapper;

import com.covoiturage.dto.vehicule.VehiculeRequest;
import com.covoiturage.dto.vehicule.VehiculeResponse;
import com.covoiturage.entity.Vehicule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(source = "conducteur.id", target = "conducteurId")
    VehiculeResponse toResponse(Vehicule vehicule);

    Vehicule toEntity(VehiculeRequest request);
}
