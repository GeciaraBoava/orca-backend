package com.geciara.orcamento.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CompositionResponseDTO {

    private Long id;
    private String description;
    private String typeName;
    private String unitMeasureName;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private BigDecimal cost;
    private List<MaterialCompositionResponseDTO> materialComposition;
}
