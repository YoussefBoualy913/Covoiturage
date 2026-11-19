package com.covoiturage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vehicules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String marque;

    private String modele;

    @Column(nullable = false, unique = true)
    private String immatriculation;

    private String couleur;

    @Column(nullable = false)
    private Integer capacite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conducteur_id", nullable = false)
    private Conducteur conducteur;

    @OneToMany(mappedBy = "vehicule")
    private List<Trajet> trajets = new ArrayList<>();
}