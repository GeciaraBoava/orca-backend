package com.geciara.orcamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    @NotNull(message = "Orçamento é obrigatório")
    private Long budgetId;
}
