package com.soa.soamesas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sesiones_mesa")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SesionMesa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Mesa mesa;

    @com.fasterxml.jackson.annotation.JsonProperty("mesaId")
    public UUID getMesaIdValue() {
        return mesa != null ? mesa.getId() : null;
    }

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @CreationTimestamp
    @Column(name = "inicio_atencion", updatable = false)
    private LocalDateTime inicioAtencion;

    @Column(name = "fin_atencion")
    private LocalDateTime finAtencion;

    @Column(name = "total_consumo", nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal totalConsumo = BigDecimal.ZERO;

    @Column(nullable = false, length = 10)
    @Builder.Default
    private String estado = "activa";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}