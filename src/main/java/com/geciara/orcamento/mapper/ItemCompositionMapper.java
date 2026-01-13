package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.ItemCompositionDTO;
import com.geciara.orcamento.dto.MaterialCompositionResponseDTO;
import com.geciara.orcamento.dto.MaterialCompositionUpdateDTO;
import com.geciara.orcamento.model.entitys.ItemComposition;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ItemCompositionMapper {

    public ItemComposition toEntity(ItemCompositionDTO dto) {
        ItemComposition item = new ItemComposition();
        item.setDescription(dto.getDescription());
        item.setType(dto.getType());
        item.setQuantity(dto.getQuantity());
        // composition e material serão setados no service
        return item;
    }

    public MaterialCompositionResponseDTO toResponseDTO(ItemComposition entity) {
        MaterialCompositionResponseDTO dto = new MaterialCompositionResponseDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType().name());
        if (entity.getType().name().equals("MATERIAL") && entity.getMaterial() != null) {
            dto.setMaterialId(entity.getMaterial().getId());
            dto.setMaterialDescription(entity.getMaterial().getDescription());
        } else if (entity.getType().name().equals("COMPOSITION") && entity.getComposition() != null) {
            dto.setCompositionId(entity.getComposition().getId());
            dto.setCompositionDescription(entity.getComposition().getDescription());
        }
        dto.setQuantity(entity.getQuantity());
        dto.setCost(entity.getCost(LocalDate.now()));
        return dto;
    }

    public ItemComposition updateFromDTO(MaterialCompositionUpdateDTO dto, ItemComposition entity) {
        if (dto.getQuantity() != null) {
            entity.setQuantity(dto.getQuantity());
        }
        return entity;
    }
}
