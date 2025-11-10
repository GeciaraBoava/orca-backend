package com.geciara.orcamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MaterialRequestDTO {

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    @NotNull(message = "Tipo do material é obrigatório")
    private Long materialTypeId;

    @NotNull(message = "Unidade de medida é obrigatória")
    private Long unitMeasureId;

    @NotNull(message = "Preço atual é obrigatório")
    private BigDecimal currentPrice;

    private boolean active = true;
}
