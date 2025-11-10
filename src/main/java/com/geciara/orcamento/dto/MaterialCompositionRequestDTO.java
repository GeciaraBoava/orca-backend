package com.geciara.orcamento.dto;

import com.geciara.orcamento.model.enums.ETypeMaterialComposition;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MaterialCompositionRequestDTO {

    @NotNull
    private ETypeMaterialComposition type;

    private Long compositionId; // preenchido se type = COMPOSITION
    private Long materialId;    // preenchido se type = MATERIAL

    @NotNull
    private BigDecimal quantity;
}
