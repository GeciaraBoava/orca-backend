package com.geciara.orcamento.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialTypeResponseDTO {

    private Long id;
    private String name;
    private String description;
    private boolean active;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
}
