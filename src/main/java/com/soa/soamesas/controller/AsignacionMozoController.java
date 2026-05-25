package com.soa.soamesas.controller;

import com.soa.soamesas.entity.AsignacionMozo;
import com.soa.soamesas.repository.AsignacionMozoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaciones-mozo")
public class AsignacionMozoController {

    private final AsignacionMozoRepository asignacionMozoRepository;

    public AsignacionMozoController(AsignacionMozoRepository asignacionMozoRepository) {
        this.asignacionMozoRepository = asignacionMozoRepository;
    }

    @GetMapping
    public List<AsignacionMozo> obtenerAsignaciones() {

        return asignacionMozoRepository.findAll();
    }
}