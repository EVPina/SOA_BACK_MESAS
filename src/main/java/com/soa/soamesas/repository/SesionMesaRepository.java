package com.soa.soamesas.repository;

import com.soa.soamesas.entity.SesionMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SesionMesaRepository extends JpaRepository<SesionMesa, UUID> {
    Optional<SesionMesa> findByMesa_IdAndEstado(UUID mesaId, String estado);
}