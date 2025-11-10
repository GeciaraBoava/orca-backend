package com.geciara.orcamento.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompositionUpdateDTO {

    private String description;
    private Long typeId;
    private Long unitMeasureId;
    private List<Long> materialCompositionIds;
}
