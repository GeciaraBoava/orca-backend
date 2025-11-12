package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.ProductRequestDTO;
import com.geciara.orcamento.dto.ProductResponseDTO;
import com.geciara.orcamento.dto.ProductUpdateDTO;
import com.geciara.orcamento.model.entitys.Budget;
import com.geciara.orcamento.model.entitys.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    private final ItemCompositionMapper itemMapper;

    public ProductMapper(ItemCompositionMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public Product toEntity(ProductRequestDTO dto, Budget budget) {
        Product product = new Product();
        product.setBudget(budget);
        product.setDescription(dto.getDescription());
        return product;
    }

    public ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setBudgetId(product.getBudget().getId());
        dto.setDescription(product.getDescription());
        dto.setCost(product.getCost());
        dto.setPrice(product.getPrice());
        return dto;
    }

    public Product updateFromDTO(ProductUpdateDTO dto, Product product, Budget budget) {
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (budget != null) product.setBudget(budget);
        return product;
    }

    public List<ProductResponseDTO> toResponseList(List<Product> products) {
        return products.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
