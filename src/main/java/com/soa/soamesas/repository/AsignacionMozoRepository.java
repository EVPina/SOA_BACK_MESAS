package com.soa.soamesas.repository;

import com.soa.soamesas.entity.AsignacionMozo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import java.util.List;
import java.util.Optional;

public interface AsignacionMozoRepository extends JpaRepository<AsignacionMozo, UUID> {
    Optional<AsignacionMozo> findByMozoIdAndMesaId(UUID mozoId, UUID mesaId);
    List<AsignacionMozo> findByMozoId(UUID mozoId);
    List<AsignacionMozo> findByMesaId(UUID mesaId);
}