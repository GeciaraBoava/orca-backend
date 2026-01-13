package com.geciara.orcamento.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UnitMeasureResponseDTO {

    private Long id;
    private String name;
    private String description;
    private boolean active;
    private LocalDateTime registeredAt;
    protected LocalDateTime updatedAt;

}