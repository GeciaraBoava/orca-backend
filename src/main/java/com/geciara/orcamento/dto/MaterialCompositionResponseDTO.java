package com.geciara.orcamento.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MaterialCompositionResponseDTO {

    private Long id;
    private String type;
    private Long compositionId;
    private String compositionDescription;
    private Long materialId;
    private String materialDescription;
    private BigDecimal quantity;
    private BigDecimal cost;
}
