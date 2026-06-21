package com.soa.soamesas.controller;

import com.soa.soamesas.entity.SesionMesa;
import com.soa.soamesas.repository.SesionMesaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sesiones-mesa")
public class SesionMesaController {

    private final SesionMesaRepository sesionMesaRepository;

    public SesionMesaController(SesionMesaRepository sesionMesaRepository) {
        this.sesionMesaRepository = sesionMesaRepository;
    }

    @GetMapping
    public List<SesionMesa> obtenerSesiones() {

        return sesionMesaRepository.findAll();
    }
}