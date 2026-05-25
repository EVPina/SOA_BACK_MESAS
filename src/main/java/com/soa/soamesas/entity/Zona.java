package com.soa.soamesas.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "zonas")
@Data
public class Zona {

    @Id
    private UUID id;

    private String nombre;

    private String descripcion;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}