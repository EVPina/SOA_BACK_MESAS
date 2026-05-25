package com.soa.soamesas.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sesiones_mesa")
@Data
public class SesionMesa {

    @Id
    private UUID id;

    @Column(name = "mesa_id")
    private UUID mesaId;

    private String estado;

    @Column(name = "hora_inicio")
    private LocalDateTime horaInicio;

    @Column(name = "hora_fin")
    private LocalDateTime horaFin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}