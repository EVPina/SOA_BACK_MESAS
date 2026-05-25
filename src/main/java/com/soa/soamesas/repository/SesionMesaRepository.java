package com.soa.soamesas.repository;

import com.soa.soamesas.entity.SesionMesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SesionMesaRepository extends JpaRepository<SesionMesa, UUID> {
}