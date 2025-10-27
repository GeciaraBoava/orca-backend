package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.MaterialRequestDTO;
import com.geciara.orcamento.dto.MaterialResponseDTO;
import com.geciara.orcamento.dto.MaterialUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.model.entitys.Material;
import com.geciara.orcamento.model.entitys.registerDetails.MaterialType;
import com.geciara.orcamento.model.entitys.registerDetails.PriceHistory;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
import com.geciara.orcamento.repository.MaterialTypeRepository;
import com.geciara.orcamento.repository.UnitMeasureRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MaterialMapper {

    private final MaterialTypeRepository materialTypeRepository;
    private final UnitMeasureRepository unitMeasureRepository;

    public MaterialMapper(MaterialTypeRepository materialTypeRepository,
                          UnitMeasureRepository unitMeasureRepository) {
        this.materialTypeRepository = materialTypeRepository;
        this.unitMeasureRepository = unitMeasureRepository;
    }


    public Material toEntity(MaterialRequestDTO dto) {

        if (dto == null) return null;

        Material material = new Material();

        material.setDescription(dto.getDescription());
        material.setCurrentPrice(dto.getCurrentPrice());
        material.setRegisteredAt(LocalDateTime.now());
        material.setActive(true);

        MaterialType materialType = materialTypeRepository.findByDescription(dto.getMaterialTypeDescription())
                        .orElseThrow(ItemNotFoundException::new);

        material.setMaterialType(materialType);

        UnitMeasure unitMeasure = unitMeasureRepository.findByDescription(dto.getUnitMeasureDescription())
                        .orElseThrow(ItemNotFoundException::new);

        material.setUnitMeasure(unitMeasure);

        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setMaterial(material);
        priceHistory.setPrice(material.getCurrentPrice());
        priceHistory.setRegisteredAt(material.getRegisteredAt());
        material.getPriceHistories().add(priceHistory);

        return material;
    }

    public Material updateFromDTO(MaterialUpdateDTO dto,
                                  Material material) {

        if (dto == null) return null;

        if(dto.getDescription() != null) material.setDescription(dto.getDescription());
        if(dto.getActive() != null) material.setActive(dto.getActive());
        material.setUpdatedAt(LocalDateTime.now());

        if(dto.getMaterialTypeDescription() != null) {
            MaterialType materialType = materialTypeRepository.findByDescription(dto.getMaterialTypeDescription())
                    .orElseThrow(ItemNotFoundException::new);

            material.setMaterialType(materialType);
        }

        if(dto.getUnitMeasureDescription() != null) {

            UnitMeasure unitMeasure = unitMeasureRepository.findByDescription(dto.getUnitMeasureDescription())
                    .orElseThrow(ItemNotFoundException::new);

            material.setUnitMeasure(unitMeasure);
        }

        if(dto.getCurrentPrice() != null) {
            material.setCurrentPrice(dto.getCurrentPrice());

            PriceHistory newPriceHistory = new PriceHistory();
            newPriceHistory.setMaterial(material);
            newPriceHistory.setPrice(dto.getCurrentPrice());
            newPriceHistory.setRegisteredAt(material.getRegisteredAt());
            material.setRegisteredAt(LocalDateTime.now());

            material.getPriceHistories().add(newPriceHistory);
        }

        return material;

    }

    public MaterialResponseDTO toResponseDTO(Material material) {
        MaterialResponseDTO dto = new MaterialResponseDTO();

        dto.setId(material.getId());
        dto.setDescription(material.getDescription());
        dto.setCurrentPrice(material.getCurrentPrice());
        dto.setActive(material.isActive());
        dto.setRegisteredAt(material.getRegisteredAt());
        dto.setUpdatedAt(material.getUpdatedAt());

        MaterialType materialType = material.getMaterialType();
        dto.setMaterialTypeDescription(materialType.getDescription());

        UnitMeasure unitMeasure = material.getUnitMeasure();
        dto.setUnitMeasureDescription(unitMeasure.getDescription());

        return dto;
    }
}