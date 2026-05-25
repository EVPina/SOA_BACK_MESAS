package com.soa.soamesas.repository;

import com.soa.soamesas.entity.AsignacionMozo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AsignacionMozoRepository extends JpaRepository<AsignacionMozo, UUID> {
}