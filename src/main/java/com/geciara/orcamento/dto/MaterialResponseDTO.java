package com.geciara.orcamento.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MaterialResponseDTO {

    private Long id;
    private String description;
    private Long materialTypeId;
    private String materialTypeDescription;
    private Long unitMeasureId;
    private String unitMeasureDescription;
    private BigDecimal currentPrice;
    private boolean active;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
}
