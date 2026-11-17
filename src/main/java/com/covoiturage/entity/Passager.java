package com.covoiturage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "passagers")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Passager extends User {

    @OneToMany(mappedBy = "passager")
    private List<Reservation> reservations = new ArrayList<>();
}