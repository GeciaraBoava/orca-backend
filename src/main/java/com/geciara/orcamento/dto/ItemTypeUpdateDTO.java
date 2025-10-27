package com.geciara.orcamento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemTypeUpdateDTO {

    @NotNull
    private Long id;

    private String description;
    private boolean active;

}
