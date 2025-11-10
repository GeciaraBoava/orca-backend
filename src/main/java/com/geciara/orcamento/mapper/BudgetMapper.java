package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.BudgetRequestDTO;
import com.geciara.orcamento.dto.BudgetResponseDTO;
import com.geciara.orcamento.dto.BudgetUpdateDTO;
import com.geciara.orcamento.model.entitys.Budget;
import com.geciara.orcamento.model.entitys.Customer;
import com.geciara.orcamento.model.entitys.Product;
import com.geciara.orcamento.service.CustomerService;
import com.geciara.orcamento.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BudgetMapper {

    private final CustomerService customerService;
    private final ProductService productService;

    public BudgetMapper(CustomerService customerService, ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
    }

    public Budget toEntity(BudgetRequestDTO dto) {
        Budget budget = new Budget();
        budget.setDate(dto.getDate());
        budget.setDateReference(dto.getDateReference());

        Customer customer = customerService.findEntityById(dto.getCustomerId());
        budget.setCustomer(customer);

        List<Product> products = dto.getProductIds().stream()
                .map(productService::findProductById)
                .collect(Collectors.toList());
        budget.setProducts(products);

        budget.setTaxes(dto.getTaxes());

        // Atualiza custo e preço de cada produto
        products.forEach(p -> p.updateCostAndPrice(budget, dto.getDate()));

        // Atualiza total do orçamento
        budget.setTotalCost(products.stream()
                .map(Product::getCost)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));
        budget.setTotalPrice(products.stream()
                .map(Product::getPrice)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));

        return budget;
    }

    public BudgetResponseDTO toResponseDTO(Budget budget) {
        BudgetResponseDTO dto = new BudgetResponseDTO();
        dto.setId(budget.getId());
        dto.setDate(budget.getDate());
        dto.setDateReference(budget.getDateReference());
        dto.setCustomerId(budget.getCustomer().getId());
        dto.setProducts(budget.getProducts());
        dto.setTaxes(budget.getTaxes());
        dto.setTotalCost(budget.getTotalCost());
        dto.setTotalPrice(budget.getTotalPrice());
        dto.setActive(budget.isActive());
        dto.setStatus(budget.getStatus());
        dto.setRegisteredAt(budget.getRegisteredAt());
        dto.setUpdatedAt(budget.getUpdatedAt());
        return dto;
    }

    public Budget updateFromDTO(BudgetUpdateDTO dto, Budget budget) {
        if (dto.getDate() != null) budget.setDate(dto.getDate());
        if (dto.getDateReference() != null) budget.setDateReference(dto.getDateReference());
        if (dto.getTaxes() != null) budget.setTaxes(dto.getTaxes());
        if (dto.getStatus() != null) budget.setStatus(dto.getStatus());
        if (dto.getActive() != null) budget.setActive(dto.getActive());

        if (dto.getProductIds() != null && !dto.getProductIds().isEmpty()) {
            List<Product> products = dto.getProductIds().stream()
                    .map(productService::findProductById)
                    .collect(Collectors.toList());
            budget.setProducts(products);
        }

        // Atualiza custo e preço de cada produto
        budget.getProducts().forEach(p -> p.updateCostAndPrice(budget, budget.getDate()));

        // Atualiza total do orçamento
        budget.setTotalCost(budget.getProducts().stream()
                .map(Product::getCost)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));
        budget.setTotalPrice(budget.getProducts().stream()
                .map(Product::getPrice)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));

        return budget;
    }
}
