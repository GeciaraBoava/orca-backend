package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.ProductRequestDTO;
import com.geciara.orcamento.dto.ProductResponseDTO;
import com.geciara.orcamento.dto.ProductUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.ProductMapper;
import com.geciara.orcamento.model.entitys.Composition;
import com.geciara.orcamento.model.entitys.Product;
import com.geciara.orcamento.model.entitys.registerDetails.MaterialType;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
import com.geciara.orcamento.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final MaterialTypeService materialTypeService;
    private final UnitMeasureService unitMeasureService;
    private final CompositionService compositionService;

    public ProductService(
            ProductRepository productRepository,
            ProductMapper productMapper,
            MaterialTypeService materialTypeService,
            UnitMeasureService unitMeasureService,
            CompositionService compositionService
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.materialTypeService = materialTypeService;
        this.unitMeasureService = unitMeasureService;
        this.compositionService = compositionService;
    }

    public ProductResponseDTO save(ProductRequestDTO dto) {
        MaterialType materialType = materialTypeService.findMaterialTypeById(dto.getMaterialTypeId());
        UnitMeasure unitMeasure = unitMeasureService.findUnitMeasureById(dto.getUnitMeasureId());
        List<Composition> compositionList = dto.getCompositionsIds()
                .stream()
                .map(compositionService::findProductItemById)
                .toList();

        Product product = productMapper.toEntity(dto,materialType, unitMeasure, compositionList);
        product = productRepository.save(product);

        return productMapper.toResponseDTO(product);
    }

    public List<ProductResponseDTO> listAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        return productMapper.toResponseDTO(product);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    public ProductResponseDTO update(Long id, ProductUpdateDTO dto) {
        MaterialType materialType = materialTypeService.findMaterialTypeById(dto.getMaterialTypeId());
        UnitMeasure unitMeasure = unitMeasureService.findUnitMeasureById(dto.getUnitMeasureId());
        List<Composition> compositionList = dto.getProductItemIds()
                .stream()
                .map(compositionService::findProductItemById)
                .toList();

        Product product = productRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        Product updatedProduct = productMapper.updateFromDTO(dto, product, materialType, unitMeasure, compositionList);
        productRepository.save(updatedProduct);
        return productMapper.toResponseDTO(updatedProduct);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ItemNotFoundException();
        }
        productRepository.deleteById(id);
    }

}
