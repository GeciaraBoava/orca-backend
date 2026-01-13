package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.UnitMeasureRequestDTO;
import com.geciara.orcamento.dto.UnitMeasureResponseDTO;
import com.geciara.orcamento.dto.UnitMeasureUpdateDTO;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UnitMeasureMapper {

    public UnitMeasure toEntity(UnitMeasureRequestDTO dto) {

        if (dto == null) return null;

        UnitMeasure unitMeasure = new UnitMeasure();

        unitMeasure.setName(dto.getName());
        unitMeasure.setDescription(dto.getDescription());
        unitMeasure.setActive(true);
        unitMeasure.setRegisteredAt(LocalDateTime.now());

        return unitMeasure;
    }

    public UnitMeasure updateFromDTO(UnitMeasureUpdateDTO dto,
                                     UnitMeasure unitMeasure) {

        if (dto == null) return null;

        if(dto.getName() != null) unitMeasure.setName(dto.getName());
        if(dto.getDescription() != null) unitMeasure.setDescription(dto.getDescription());
        if(dto.getActive() != null) unitMeasure.setActive(dto.getActive());
        unitMeasure.setUpdatedAt(LocalDateTime.now());

        return unitMeasure;
    }

    public UnitMeasureResponseDTO toResponseDTO(UnitMeasure unitMeasure) {
        UnitMeasureResponseDTO dto = new UnitMeasureResponseDTO();

        dto.setId(unitMeasure.getId());
        dto.setName(unitMeasure.getName());
        dto.setDescription(unitMeasure.getDescription());
        dto.setActive(unitMeasure.isActive());
        dto.setRegisteredAt(unitMeasure.getRegisteredAt());
        dto.setUpdatedAt(unitMeasure.getUpdatedAt());

        return dto;
    }

}