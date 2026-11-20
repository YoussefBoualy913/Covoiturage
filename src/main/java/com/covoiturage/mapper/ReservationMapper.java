package com.covoiturage.mapper;

import com.covoiturage.dto.reservation.ReservationRequest;
import com.covoiturage.dto.reservation.ReservationResponse;
import com.covoiturage.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "passager.id", target = "passagerId")
    @Mapping(source = "trajet.id", target = "trajetId")
    @Mapping(source = "pointMontee.ville", target = "villeMontee")
    @Mapping(source = "pointDescente.ville", target = "villeDescente")
    ReservationResponse toResponse(Reservation reservation);

    List<ReservationResponse> toResponseList(List<Reservation> reservations);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passager", ignore = true)
    @Mapping(target = "trajet", ignore = true)
    @Mapping(target = "pointMontee", ignore = true)
    @Mapping(target = "pointDescente", ignore = true)
    @Mapping(target = "dateReservation", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "montantTotal", ignore = true)
    Reservation toEntity(ReservationRequest request);
}
