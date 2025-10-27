package com.geciara.orcamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MaterialResponseDTO {

    private Long id;
    private String description;
    private String materialTypeDescription;
    private String unitMeasureDescription;
    private BigDecimal currentPrice;
    private boolean active;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime registeredAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}