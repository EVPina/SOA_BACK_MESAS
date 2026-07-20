package com.soa.soamesas.controller;

import com.soa.soamesas.entity.AsignacionMozo;
import com.soa.soamesas.repository.AsignacionMozoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.soa.soamesas.dto.AsignacionMozoRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/asignaciones-mozo")
public class AsignacionMozoController {

    private final AsignacionMozoRepository asignacionMozoRepository;

    public AsignacionMozoController(AsignacionMozoRepository asignacionMozoRepository) {
        this.asignacionMozoRepository = asignacionMozoRepository;
    }

    @GetMapping
    public List<AsignacionMozo> obtenerAsignaciones() {
        return asignacionMozoRepository.findAll();
    }

    @GetMapping("/mozo/{mozoId}")
    public List<AsignacionMozo> obtenerPorMozo(@PathVariable UUID mozoId) {
        return asignacionMozoRepository.findByMozoId(mozoId);
    }

    @GetMapping("/mesa/{mesaId}")
    public List<AsignacionMozo> obtenerPorMesa(@PathVariable UUID mesaId) {
        return asignacionMozoRepository.findByMesaId(mesaId);
    }

    @PostMapping
    public ResponseEntity<?> asignarMesaAMozo(@RequestBody AsignacionMozoRequestDTO request) {
        Optional<AsignacionMozo> existente = asignacionMozoRepository.findByMozoIdAndMesaId(request.getMozoId(), request.getMesaId());
        
        if (existente.isPresent()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "La mesa ya está asignada a este mozo.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        AsignacionMozo nuevaAsignacion = new AsignacionMozo();
        nuevaAsignacion.setId(UUID.randomUUID());
        nuevaAsignacion.setMozoId(request.getMozoId());
        nuevaAsignacion.setMesaId(request.getMesaId());
        nuevaAsignacion.setEstado("ACTIVO");

        AsignacionMozo guardado = asignacionMozoRepository.save(nuevaAsignacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @DeleteMapping("/mozo/{mozoId}/mesa/{mesaId}")
    public ResponseEntity<?> desasignarMesa(@PathVariable UUID mozoId, @PathVariable UUID mesaId) {
        Optional<AsignacionMozo> asignacion = asignacionMozoRepository.findByMozoIdAndMesaId(mozoId, mesaId);
        
        if (asignacion.isPresent()) {
            asignacionMozoRepository.delete(asignacion.get());
            return ResponseEntity.noContent().build();
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Asignación no encontrada.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}