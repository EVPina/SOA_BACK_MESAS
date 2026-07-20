package com.soa.soamesas.controller;

import com.soa.soamesas.entity.Mesa;
import com.soa.soamesas.repository.MesaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mesas")
public class MesaController {

    private final MesaRepository mesaRepository;

    public MesaController(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    // listar mesas
    @GetMapping
    public List<Mesa> obtenerMesas() {
        return mesaRepository.findAll();
    }

    // obtener mesa por ID
    @GetMapping("/{id}")
    public Mesa obtenerMesa(@PathVariable UUID id) {

        return mesaRepository.findById(id)
                .orElse(null);
    }

    // asignar mesa
     @PutMapping("/{id}/asignar")
    public ResponseEntity<Map<String, String>> asignarMesa(@PathVariable UUID id) {
        Mesa mesa = mesaRepository.findById(id).orElse(null);
        
        if (mesa == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Mesa no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        mesa.setEstado("OCUPADA");
        Integer ocupacion = mesa.getOcupacionActual();
        if (ocupacion == null) ocupacion = 0;
        mesa.setOcupacionActual(ocupacion + 1);
        mesaRepository.save(mesa);
        
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Mesa asignada correctamente");
        response.put("mesaId", id.toString());
        response.put("estado", "OCUPADA");
        return ResponseEntity.ok(response);
    }

     @GetMapping("/ocupacion")
    public ResponseEntity<Map<String, Object>> mapaOcupacion() {
        List<Mesa> todas = mesaRepository.findAll();
        long ocupadas = todas.stream().filter(m -> "OCUPADA".equals(m.getEstado())).count();
        long libres = todas.stream().filter(m -> "LIBRE".equals(m.getEstado())).count();
        
        Map<String, Object> mapa = new HashMap<>();
        mapa.put("total", todas.size());
        mapa.put("ocupadas", ocupadas);
        mapa.put("libres", libres);
        mapa.put("mesas", todas);
        
        return ResponseEntity.ok(mapa);
    }

   // liberar mesa
    @PutMapping("/{id}/liberar")
    public String liberarMesa(@PathVariable UUID id) {

        Mesa mesa = mesaRepository.findById(id)
                .orElse(null);

        if (mesa == null) {
            return "Mesa no encontrada";
        }

        mesa.setEstado("LIBRE");

        // IMPORTANTE
        mesa.setOcupacionActual(0);

        mesaRepository.save(mesa);

        return "Mesa liberada correctamente";
    }

        // ✅ NUEVO: Obtener solo mesas disponibles (LIBRE)
    @GetMapping("/disponibles")
    public List<Mesa> mesasDisponibles() {
        return mesaRepository.findByEstado("LIBRE");
    }
}