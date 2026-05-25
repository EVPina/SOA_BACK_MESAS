package com.soa.soamesas.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mesas")
@Data
public class Mesa {

    @Id
    private UUID id;

    private Integer numero;

    private Integer capacidad;

    private String estado;

    @Column(name = "ocupacion_actual")
    private Integer ocupacionActual;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}