package com.covoiturage.mapper;

import com.covoiturage.dto.conducteur.ConducteurResponse;
import com.covoiturage.dto.vehicule.VehiculeRequest;
import com.covoiturage.dto.vehicule.VehiculeResponse;
import com.covoiturage.entity.Conducteur;
import com.covoiturage.entity.Vehicule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(source = "conducteur.nom", target = "nomConducteur")
    @Mapping(source = "conducteur.prenom", target = "prenomConducteur")
    VehiculeResponse toResponse(Vehicule vehicule);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trajets", ignore = true)
    Vehicule toEntity(VehiculeRequest request);

    List<VehiculeResponse> toResponseList(List<Vehicule> vehicules);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "conducteur", ignore = true)
    @Mapping(target = "trajets", ignore = true)
    void updateFromRequest(
            VehiculeRequest request,
            @MappingTarget Vehicule vehicule
    );
}