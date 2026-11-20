package com.covoiturage.repository;

import com.covoiturage.entity.PointArret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PointArretRepository extends JpaRepository<PointArret, UUID> {

}
