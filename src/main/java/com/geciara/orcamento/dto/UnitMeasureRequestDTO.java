package com.geciara.orcamento.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UnitMeasureRequestDTO {

    @NotBlank(message = "Símbolo é obrigatório")
    private String name;

    @NotBlank(message = "Nome é obrigatório")
    private String description;

}