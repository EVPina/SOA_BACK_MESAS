package com.soa.soamesas.controller;

import com.soa.soamesas.entity.Mesa;
import com.soa.soamesas.repository.MesaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mesas")
@Tag(name = "Mesas", description = "Operaciones relacionadas con las mesas")
public class MesaController {

    private final MesaRepository mesaRepository;

    public MesaController(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    // listar mesas
    @GetMapping
     @Operation(summary = "Obtener todas las mesas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mesas obtenidas exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mesas no encontradas")
    })
    public List<Mesa> obtenerMesas() {
        return mesaRepository.findAll();
    }

    // obtener mesa por ID
    @GetMapping("/{id}")
     @Operation(summary = "Obtener una mesa por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mesa obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
    public Mesa obtenerMesa(@PathVariable UUID id) {

        return mesaRepository.findById(id)
                .orElse(null);
    }

    // asignar mesa
     @PutMapping("/{id}/asignar")
     @Operation(summary = "Asignar una mesa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mesa asignada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
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
     @Operation(summary = "Obtener el mapa de ocupación de mesas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mapa de ocupación obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mesas no encontradas")
    })
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
    @Operation(summary = "Liberar una mesa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mesa liberada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
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
    @Operation(summary = "Obtener todas las mesas disponibles (LIBRE)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mesas disponibles obtenidas exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mesas no encontradas")
    })
    public List<Mesa> mesasDisponibles() {
        return mesaRepository.findByEstado("LIBRE");
    }
}