package com.soa.soamesas.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "asignaciones_mozo")
@Data
public class AsignacionMozo {

    @Id
    private UUID id;

    @Column(name = "sesion_mesa_id")
    private UUID sesionMesaId;

    @Column(name = "mozo_id")
    private UUID mozoId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}