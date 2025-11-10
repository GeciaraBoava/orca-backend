package com.geciara.orcamento.dto;

import com.geciara.orcamento.model.entitys.ItemComposition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompositionRequestDTO {

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    @NotNull(message = "Tipo é obrigatório")
    private Long typeId;

    @NotNull(message = "Unidade de medida é obrigatória")
    private Long unitMeasureId;

    @NotNull(message = "Lista de materiais é obrigatória")
    private List<ItemComposition> materialComposition;
}
