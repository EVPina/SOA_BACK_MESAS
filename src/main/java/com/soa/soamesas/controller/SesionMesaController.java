package com.soa.soamesas.controller;

import com.soa.soamesas.dto.SesionMesaRequest;
import com.soa.soamesas.entity.SesionMesa;
import com.soa.soamesas.repository.SesionMesaRepository;
import com.soa.soamesas.service.SesionMesaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sesiones-mesa")
public class SesionMesaController {

    private final SesionMesaRepository sesionMesaRepository;
    private final SesionMesaService sesionMesaService;

    public SesionMesaController(SesionMesaRepository sesionMesaRepository, SesionMesaService sesionMesaService) {
        this.sesionMesaRepository = sesionMesaRepository;
        this.sesionMesaService = sesionMesaService;
    }

    @GetMapping
    public List<SesionMesa> obtenerSesiones() {
        return sesionMesaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SesionMesa> obtenerSesion(@PathVariable UUID id) {
        return sesionMesaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activa/mesa/{mesaId}")
    public ResponseEntity<SesionMesa> obtenerSesionActivaPorMesa(@PathVariable UUID mesaId) {
        return sesionMesaRepository.findByMesa_IdAndEstado(mesaId, "activa")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/iniciar")
    public ResponseEntity<SesionMesa> iniciarSesion(@RequestBody SesionMesaRequest request) {
        return new ResponseEntity<>(sesionMesaService.iniciarSesion(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/consumo")
    public ResponseEntity<SesionMesa> agregarConsumo(@PathVariable UUID id, @RequestParam BigDecimal monto) {
        return ResponseEntity.ok(sesionMesaService.agregarConsumo(id, monto));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarSesion(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(sesionMesaService.finalizarSesion(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Map.of("error", e.getMessage(), "cause", e.getCause() != null ? e.getCause().getMessage() : "No cause"));
        }
    }
}