package com.soa.soamesas.controller;

import com.soa.soamesas.entity.SesionMesa;
import com.soa.soamesas.repository.SesionMesaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sesiones-mesa")
@Tag(name = "Sesiones de Mesa", description = "Operaciones relacionadas con las sesiones de mesa")
public class SesionMesaController {

    private final SesionMesaRepository sesionMesaRepository;

    public SesionMesaController(SesionMesaRepository sesionMesaRepository) {
        this.sesionMesaRepository = sesionMesaRepository;
    }

    @GetMapping
     @Operation(summary = "Obtener todas las sesiones de mesa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sesiones obtenidas exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sesiones no encontradas")
    })
    public List<SesionMesa> obtenerSesiones() {

        return sesionMesaRepository.findAll();
    }
}