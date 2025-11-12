package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.BudgetRequestDTO;
import com.geciara.orcamento.dto.BudgetResponseDTO;
import com.geciara.orcamento.dto.BudgetUpdateDTO;
import com.geciara.orcamento.model.entitys.Budget;
import com.geciara.orcamento.model.entitys.Customer;
import com.geciara.orcamento.model.entitys.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BudgetMapper {

    public Budget toEntity(BudgetRequestDTO dto, Customer customer, List<Product> products) {
        Budget budget = new Budget();
        budget.setDate(dto.getDate());
        budget.setDateReference(dto.getDateReference());
        budget.setCustomer(customer);
        budget.setTaxes(dto.getTaxes());

        products.forEach(budget::addProduct);

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

    public Budget updateFromDTO(BudgetUpdateDTO dto, Budget budget, List<Product> products) {
        if (dto.getDate() != null) budget.setDate(dto.getDate());
        if (dto.getDateReference() != null) budget.setDateReference(dto.getDateReference());
        if (dto.getTaxes() != null) budget.setTaxes(dto.getTaxes());
        if (dto.getStatus() != null) budget.setStatus(dto.getStatus());
        if (dto.getActive() != null) budget.setActive(dto.getActive());

        budget.getProducts().clear();
        products.forEach(budget::addProduct);

        return budget;
    }
}
