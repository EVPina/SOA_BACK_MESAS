package com.soa.soamesas.service;

import com.soa.soamesas.dto.SesionMesaRequest;
import com.soa.soamesas.entity.Mesa;
import com.soa.soamesas.entity.SesionMesa;
import com.soa.soamesas.entity.AsignacionMozo;
import com.soa.soamesas.repository.MesaRepository;
import com.soa.soamesas.repository.SesionMesaRepository;
import com.soa.soamesas.repository.AsignacionMozoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class SesionMesaService {

    private final SesionMesaRepository sesionMesaRepository;
    private final MesaRepository mesaRepository;
    private final AsignacionMozoRepository asignacionMozoRepository;

    public SesionMesaService(SesionMesaRepository sesionMesaRepository, MesaRepository mesaRepository, AsignacionMozoRepository asignacionMozoRepository) {
        this.sesionMesaRepository = sesionMesaRepository;
        this.mesaRepository = mesaRepository;
        this.asignacionMozoRepository = asignacionMozoRepository;
    }

    @org.springframework.transaction.annotation.Transactional
    public SesionMesa iniciarSesion(SesionMesaRequest request) {
        Mesa mesa = mesaRepository.findById(request.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        // Verificar si la mesa ya tiene una sesión activa
        Optional<SesionMesa> sesionActivaOpt = sesionMesaRepository.findByMesa_IdAndEstado(request.getMesaId(), "activa");

        SesionMesa sesionMesa;
        if (sesionActivaOpt.isPresent()) {
            sesionMesa = sesionActivaOpt.get();
        } else {
            sesionMesa = SesionMesa.builder()
                    .mesa(mesa)
                    .clienteId(request.getClienteId())
                    .estado("activa")
                    .totalConsumo(BigDecimal.ZERO)
                    .build();
            sesionMesa = sesionMesaRepository.save(sesionMesa);

            // Actualizar estado de la mesa a OCUPADA
            mesa.setEstado("OCUPADA");
            mesa.setOcupacionActual(mesa.getOcupacionActual() + 1); // asumiendo que al menos un cliente ocupa
            mesaRepository.save(mesa);
        }

        // Vincular el ID de esta sesión a la asignación del mozo existente
        List<AsignacionMozo> asignaciones = asignacionMozoRepository.findByMesaId(mesa.getId());
        for (AsignacionMozo asignacion : asignaciones) {
            asignacion.setSesionMesaId(sesionMesa.getId());
            asignacionMozoRepository.save(asignacion);
        }

        return sesionMesa;
    }

    @org.springframework.transaction.annotation.Transactional
    public SesionMesa agregarConsumo(UUID sesionMesaId, BigDecimal monto) {
        SesionMesa sesionMesa = sesionMesaRepository.findById(sesionMesaId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
        
        sesionMesa.setTotalConsumo(sesionMesa.getTotalConsumo().add(monto));
        return sesionMesaRepository.save(sesionMesa);
    }

    @org.springframework.transaction.annotation.Transactional
    public SesionMesa finalizarSesion(UUID sesionMesaId) {
        SesionMesa sesionMesa = sesionMesaRepository.findById(sesionMesaId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
        
        if ("finalizada".equals(sesionMesa.getEstado())) {
            return sesionMesa;
        }

        sesionMesa.setEstado("finalizada");
        sesionMesa.setFinAtencion(java.time.LocalDateTime.now());
        sesionMesa = sesionMesaRepository.save(sesionMesa);

        // Disminuir ocupación de la mesa
        Mesa mesa = sesionMesa.getMesa();
        if (mesa != null) {
            int ocupacion = mesa.getOcupacionActual() != null ? mesa.getOcupacionActual() : 0;
            if (ocupacion > 0) {
                mesa.setOcupacionActual(ocupacion - 1);
            }
            if (mesa.getOcupacionActual() == 0) {
                mesa.setEstado("LIBRE");
            }
            mesaRepository.save(mesa);
        }

        return sesionMesa;
    }
}
