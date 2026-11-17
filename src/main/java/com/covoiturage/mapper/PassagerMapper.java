package com.covoiturage.mapper;

import com.covoiturage.dto.passager.PassagerRequest;
import com.covoiturage.dto.passager.PassagerResponse;
import com.covoiturage.entity.Passager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassagerMapper {
    @Mapping(target = "isActive" , ignore = true)
    PassagerResponse toResponse(Passager passager);

    Passager toEntity(PassagerRequest request);
}
