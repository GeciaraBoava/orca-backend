package com.geciara.orcamento.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaterialTypeRequestDTO {

    @NotBlank(message = "Tipo de material é obrigatório")
    private String name;

    @NotBlank(message = "Nome é obrigatório")
    private String description;

}
