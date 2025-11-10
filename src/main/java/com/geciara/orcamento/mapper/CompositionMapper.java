package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.CompositionRequestDTO;
import com.geciara.orcamento.dto.CompositionResponseDTO;
import com.geciara.orcamento.dto.CompositionUpdateDTO;
import com.geciara.orcamento.dto.MaterialCompositionResponseDTO;
import com.geciara.orcamento.model.entitys.Composition;
import com.geciara.orcamento.model.entitys.ItemComposition;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class CompositionMapper {

    public Composition toEntity(CompositionRequestDTO dto) {
        Composition composition = new Composition();
        composition.setDescription(dto.getDescription());
        composition.setMaterialCompositions(dto.getMaterialComposition());
        return composition;
    }

    public CompositionResponseDTO toResponseDTO(Composition entity) {
        CompositionResponseDTO dto = new CompositionResponseDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setTypeName(entity.getType() != null ? entity.getType().getDescription() : null);
        dto.setUnitMeasureName(entity.getUnitMeasure() != null ? entity.getUnitMeasure().getDescription() : null);
        dto.setRegisteredAt(entity.getRegisteredAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCost(entity.getCost(LocalDate.now()));

        dto.setMaterialComposition(entity.getMaterialCompositions()
                .stream()
                .map(this::toMaterialCompositionDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private MaterialCompositionResponseDTO toMaterialCompositionDTO(ItemComposition mc) {
        MaterialCompositionResponseDTO dto = new MaterialCompositionResponseDTO();
        dto.setId(mc.getId());
        dto.setType(mc.getType().name());
        if (mc.getType().name().equals("MATERIAL") && mc.getMaterial() != null) {
            dto.setMaterialId(mc.getMaterial().getId());
            dto.setMaterialDescription(mc.getMaterial().getDescription());
        } else if (mc.getType().name().equals("COMPOSITION") && mc.getComposition() != null) {
            dto.setMaterialId(mc.getComposition().getId());
            dto.setMaterialDescription(mc.getComposition().getDescription());
        }
        dto.setQuantity(mc.getQuantity());
        dto.setCost(mc.getCost(LocalDate.now()));
        return dto;
    }

    public Composition updateFromDTO(CompositionUpdateDTO dto, Composition entity) {
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        // type, unitMeasure e materialComposition são gerenciados no Service
        return entity;
    }
}
