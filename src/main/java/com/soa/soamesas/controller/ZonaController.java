package com.soa.soamesas.controller;

import com.soa.soamesas.entity.Zona;
import com.soa.soamesas.repository.ZonaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/zonas")
@Tag(name = "Zonas", description = "Operaciones relacionadas con las zonas")
public class ZonaController {

    private final ZonaRepository zonaRepository;

    public ZonaController(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    @GetMapping
     @Operation(summary = "Obtener todas las zonas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Zonas obtenidas exitosamente"),
        @ApiResponse(responseCode = "404", description = "Zonas no encontradas")
    })
    public List<Zona> obtenerZonas() {
        return zonaRepository.findAll();
    }
}