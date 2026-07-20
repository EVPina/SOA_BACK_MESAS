package com.soa.soamesas.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class AsignacionMozoRequestDTO {
    private UUID mozoId;
    private UUID mesaId;
}
