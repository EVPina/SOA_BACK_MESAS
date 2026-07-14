package com.soa.soamesas.controller;

import com.soa.soamesas.entity.AsignacionMozo;
import com.soa.soamesas.repository.AsignacionMozoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/asignaciones-mozo")
@Tag(name = "Asignación de Mozo", description = "Operaciones relacionadas con la asignación de mozos a mesas")
public class AsignacionMozoController {

    private final AsignacionMozoRepository asignacionMozoRepository;

    public AsignacionMozoController(AsignacionMozoRepository asignacionMozoRepository) {
        this.asignacionMozoRepository = asignacionMozoRepository;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las asignaciones de mozo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignaciones obtenidas exitosamente"),
        @ApiResponse(responseCode = "404", description = "Asignaciones no encontradas")
    })
    public List<AsignacionMozo> obtenerAsignaciones() {

        return asignacionMozoRepository.findAll();
    }
}