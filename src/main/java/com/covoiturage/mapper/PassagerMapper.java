
package com.covoiturage.mapper;

import com.covoiturage.dto.passager.PassagerRequest;
import com.covoiturage.dto.passager.PassagerResponse;
import com.covoiturage.entity.Passager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassagerMapper {

    @Mapping(source = "active", target = "isActive")
    PassagerResponse toResponse(Passager passager);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Passager toEntity(PassagerRequest request);

    List<PassagerResponse> toResponseList(List<Passager> passagers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    void updateFromRequest(
            PassagerRequest request,
            @MappingTarget Passager passager
    );
}
