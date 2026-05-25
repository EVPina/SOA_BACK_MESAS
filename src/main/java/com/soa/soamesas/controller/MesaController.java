package com.soa.soamesas.controller;

import com.soa.soamesas.entity.Mesa;
import com.soa.soamesas.repository.MesaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mesas")
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

    // mesas libres
    @GetMapping("/libres")
    public List<Mesa> mesasLibres() {

        return mesaRepository.findByEstado("LIBRE");
    }

    // mesas ocupadas
    @GetMapping("/ocupadas")
    public List<Mesa> mesasOcupadas() {

        return mesaRepository.findByEstado("OCUPADA");
    }

    // ocupar mesa
    @PutMapping("/{id}/ocupar")
    public String ocuparMesa(@PathVariable UUID id) {

        Mesa mesa = mesaRepository.findById(id)
                .orElse(null);

        if (mesa == null) {
            return "Mesa no encontrada";
        }

        mesa.setEstado("OCUPADA");

        Integer ocupacion = mesa.getOcupacionActual();

        if (ocupacion == null) {
            ocupacion = 0;
        }

        mesa.setOcupacionActual(ocupacion + 1);

        mesaRepository.save(mesa);

        return "Mesa ocupada correctamente";
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
}