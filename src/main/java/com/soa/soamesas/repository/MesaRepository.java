package com.soa.soamesas.repository;

import com.soa.soamesas.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MesaRepository extends JpaRepository<Mesa, UUID> {

    List<Mesa> findByEstado(String estado);

}