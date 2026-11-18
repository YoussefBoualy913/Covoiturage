package com.covoiturage.mapper;

import com.covoiturage.dto.conducteur.ConducteurRequest;
import com.covoiturage.dto.conducteur.ConducteurResponse;
import com.covoiturage.entity.Conducteur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConducteurMapper {
    @Mapping(source = "active", target = "isActive")
    ConducteurResponse toResponse(Conducteur conducteur);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "trajets", ignore = true)

    Conducteur toEntity(ConducteurRequest request);

    List<ConducteurResponse> toResponseList(List<Conducteur> conducteurs);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "trajets", ignore = true)
    void updateFromRequest(
            ConducteurRequest request,
            @MappingTarget Conducteur conducteur
    );
}
