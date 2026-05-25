package com.soa.soamesas.controller;

import com.soa.soamesas.entity.Zona;
import com.soa.soamesas.repository.ZonaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zonas")
public class ZonaController {

    private final ZonaRepository zonaRepository;

    public ZonaController(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    @GetMapping
    public List<Zona> obtenerZonas() {
        return zonaRepository.findAll();
    }
}