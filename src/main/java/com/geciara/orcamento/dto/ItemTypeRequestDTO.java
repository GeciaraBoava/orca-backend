package com.geciara.orcamento.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemTypeRequestDTO {

    @NotBlank(message = "Tipo de item é obrigatório")
    private String name;

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    private Boolean active;

}
