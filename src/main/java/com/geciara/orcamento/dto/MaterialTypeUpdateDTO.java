package com.geciara.orcamento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaterialTypeUpdateDTO {

    @NotNull
    private Long id;

    private String description;
    private Boolean active;
}
