package com.soa.soamesas.repository;

import com.soa.soamesas.entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ZonaRepository extends JpaRepository<Zona, UUID> {
}