package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.ProductRequestDTO;
import com.geciara.orcamento.dto.ProductResponseDTO;
import com.geciara.orcamento.dto.ProductUpdateDTO;
import com.geciara.orcamento.model.entitys.Budget;
import com.geciara.orcamento.model.entitys.Product;
import com.geciara.orcamento.service.BudgetService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final BudgetService budgetService;

    public ProductMapper(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    public Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        Budget budget = budgetService.findEntityById(dto.getBudgetId());
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

    public Product updateFromDTO(ProductUpdateDTO dto, Product product) {
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getBudgetId() != null) {
            Budget budget = budgetService.findEntityById(dto.getBudgetId());
            product.setBudget(budget);
        }
        return product;
    }

    public List<ProductResponseDTO> toResponseList(List<Product> products) {
        return products.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
