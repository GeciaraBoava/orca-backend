package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.MaterialTypeRequestDTO;
import com.geciara.orcamento.dto.MaterialTypeResponseDTO;
import com.geciara.orcamento.dto.MaterialTypeUpdateDTO;
import com.geciara.orcamento.model.entitys.registerDetails.MaterialType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MaterialTypeMapper {

    public MaterialType toEntity(MaterialTypeRequestDTO dto) {

        if (dto == null) return null;

        MaterialType materialType = new MaterialType();

        materialType.setDescription(dto.getDescription());
        materialType.setActive(true);
        materialType.setRegisteredAt(LocalDateTime.now());

        return materialType;
    }

    public MaterialType updateFromDTO(MaterialTypeUpdateDTO dto,
                                      MaterialType materialType) {

        if (dto == null) return null;

        if(dto.getDescription() != null) materialType.setDescription(dto.getDescription());
        if(dto.getActive() != null) materialType.setActive(dto.getActive());
        materialType.setUpdatedAt(LocalDateTime.now());

        return materialType;
    }

    public MaterialTypeResponseDTO toResponseDTO(MaterialType materialType) {
        MaterialTypeResponseDTO dto = new MaterialTypeResponseDTO();

        dto.setId(materialType.getId());
        dto.setDescription(materialType.getDescription());
        dto.setActive(materialType.isActive());
        dto.setRegisteredAt(materialType.getRegisteredAt());
        dto.setUpdatedAt(materialType.getUpdatedAt());

        return dto;
    }



}

