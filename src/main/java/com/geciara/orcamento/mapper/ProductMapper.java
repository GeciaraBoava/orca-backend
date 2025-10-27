package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.ProductRequestDTO;
import com.geciara.orcamento.dto.ProductResponseDTO;
import com.geciara.orcamento.dto.ProductUpdateDTO;
import com.geciara.orcamento.model.entitys.Composition;
import com.geciara.orcamento.model.entitys.Product;
import com.geciara.orcamento.model.entitys.registerDetails.MaterialType;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
public class ProductMapper {

    private final MaterialTypeMapper materialTypeMapper;
    private final UnitMeasureMapper unitMeasureMapper;
    private final CompositionMapper compositionMapper;

    public ProductMapper(MaterialTypeMapper materialTypeMapper, UnitMeasureMapper unitMeasureMapper, CompositionMapper compositionMapper) {
        this.materialTypeMapper = materialTypeMapper;
        this.unitMeasureMapper = unitMeasureMapper;
        this.compositionMapper = compositionMapper;
    }

    public Product toEntity(
            ProductRequestDTO dto,
            MaterialType materialType,
            UnitMeasure unitMeasure,
            List<Composition> compositionList
    ) {

        if (dto == null) return null;

        Product product = new Product();

        product.setDescription(dto.getDescription());
        product.setMaterialType(materialType);
        product.setUnitMeasure(unitMeasure);
        product.setDate(dto.getDate());
        product.setReferenceDate(dto.getReferenceDate());
        product.setCompositionList(compositionList);

        return product;
    }

    public Product updateFromDTO(
            ProductUpdateDTO dto,
            Product product,
            MaterialType materialType,
            UnitMeasure unitMeasure,
            List<Composition> compositionList
    ) {
        if (dto == null) return null;

        product.setDescription(dto.getDescription());
        product.setMaterialType(materialType);
        product.setUnitMeasure(unitMeasure);
        product.setDate(dto.getDate());
        product.setReferenceDate(dto.getReferenceDate());
        product.setCompositionList(compositionList);
        product.setCost(dto.getCost());

        return product;
    }

    public ProductResponseDTO toResponseDTO(Product product) {

        if (product == null) return null;

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setDescription(product.getDescription());
        dto.setMaterialType(materialTypeMapper.toResponseDTO(product.getMaterialType()));
        dto.setUnitMeasure(unitMeasureMapper.toResponseDTO(product.getUnitMeasure()));
        dto.setRegisteredAt(product.getRegisteredAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setDate(product.getDate());
        dto.setReferenceDate(product.getReferenceDate());
        dto.setCompositionResponseDTOS(
                product.getCompositionList()
                        .stream()
                        .map(compositionMapper::toResponseDTO)
                        .collect(Collectors.toList()));
        dto.setCost(product.getCost());

        return dto;
    }
}
