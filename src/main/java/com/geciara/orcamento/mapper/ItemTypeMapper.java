package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.ItemTypeRequestDTO;
import com.geciara.orcamento.dto.ItemTypeResponseDTO;
import com.geciara.orcamento.dto.ItemTypeUpdateDTO;
import com.geciara.orcamento.model.entitys.registerDetails.ItemType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ItemTypeMapper {

    public ItemType toEntity(ItemTypeRequestDTO dto) {

        if (dto == null) return null;

        ItemType itemType = new ItemType();

        itemType.setDescription(dto.getDescription());
        itemType.setActive(true);
        itemType.setRegisteredAt(LocalDateTime.now());
        itemType.setUpdatedAt(LocalDateTime.now());

        return itemType;
    }

    public ItemType updateFromDTO(
            ItemTypeUpdateDTO dto,
            ItemType itemType
    ) {
        if (dto == null || itemType == null) return null;

        itemType.setDescription(dto.getDescription());
        itemType.setActive(dto.isActive());
        itemType.setUpdatedAt(LocalDateTime.now());

        return itemType;
    }

    public ItemTypeResponseDTO toResponseDTO(ItemType entity) {
        if (entity == null) return null;

        ItemTypeResponseDTO dto = new ItemTypeResponseDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setActive(entity.isActive());
        dto.setRegisteredAt(entity.getRegisteredAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

}
