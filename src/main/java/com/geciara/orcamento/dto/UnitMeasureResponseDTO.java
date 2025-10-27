package com.geciara.orcamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UnitMeasureResponseDTO {

    private Long id;
    private String description;
    private boolean active;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime registeredAt;

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDateTime updatedAt;

}