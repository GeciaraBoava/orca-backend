package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.CompositionRequestDTO;
import com.geciara.orcamento.dto.CompositionResponseDTO;
import com.geciara.orcamento.dto.CompositionUpdateDTO;
import com.geciara.orcamento.model.entitys.Composition;
import org.springframework.stereotype.Component;

@Component
public class CompositionMapper {

    private final ItemTypeMapper itemTypeMapper;
    private final UnitMeasureMapper unitMeasureMapper;
    private final MaterialMapper materialMapper;

    public CompositionMapper(ItemTypeMapper itemTypeMapper,
                             UnitMeasureMapper unitMeasureMapper,
                             MaterialMapper materialMapper) {
        this.itemTypeMapper = itemTypeMapper;
        this.unitMeasureMapper = unitMeasureMapper;
        this.materialMapper = materialMapper;
    }

    public Composition toEntity(CompositionRequestDTO dto) {
        if (dto == null) return null;

        Composition composition = new Composition();
        composition.setDescription(dto.getDescription());
        composition.setType(dto.getTypeId());
        composition.setUnitMeasure(unitMeasure);
        composition.setMaterialComposition(dto.getMaterialComposition());

        return composition;
    }

    public Composition updateFromDTO(
            CompositionUpdateDTO dto,
            Composition composition
    ) {

        if (dto == null) return null;

        composition.setDescription(dto.getDescription());
        composition.setType(dto.getTypeId());

        return composition;
    }

    public CompositionResponseDTO toResponseDTO(Composition composition) {

        if (composition == null) return null;

        CompositionResponseDTO dto = new CompositionResponseDTO();

        dto.setId(composition.getId());
        dto.setDescription(composition.getDescription());
        dto.setType(itemTypeMapper.toResponseDTO(composition.getType()));
        dto.setUnitMeasure(unitMeasureMapper.toResponseDTO(composition.getUnitMeasure()));
        dto.setRegisteredAt(composition.getRegisteredAt());
        dto.setUpdatedAt(composition.getUpdatedAt());
        dto.setMaterialComposition(dto.getMaterialComposition());
        dto.setCost(composition.getCost());

        return dto;
    }

}
