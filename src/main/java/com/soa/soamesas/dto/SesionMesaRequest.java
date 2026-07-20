package com.soa.soamesas.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class SesionMesaRequest {
    private UUID mesaId;
    private UUID clienteId;
}
