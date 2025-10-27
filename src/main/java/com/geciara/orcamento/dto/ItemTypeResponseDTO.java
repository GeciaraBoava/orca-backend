package com.geciara.orcamento.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemTypeResponseDTO {

    private Long id;
    private String description;
    private boolean active;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;

}
