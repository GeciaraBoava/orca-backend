package com.geciara.orcamento.mapper;

import com.geciara.orcamento.dto.BudgetRequestDTO;
import com.geciara.orcamento.dto.BudgetResponseDTO;
import com.geciara.orcamento.dto.BudgetUpdateDTO;
import com.geciara.orcamento.model.entitys.Budget;
import com.geciara.orcamento.model.entitys.Customer;
import com.geciara.orcamento.model.entitys.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
public class BudgetMapper {

    private final CustomerMapper customerMapper;
    private final ProductMapper productMapper;

    public BudgetMapper(CustomerMapper customerMapper, ProductMapper productMapper) {
        this.customerMapper = customerMapper;
        this.productMapper = productMapper;
    }

    public Budget toEntity(
            BudgetRequestDTO dto,
            Customer customer,
            List<Product> productList
    ) {

        if (dto == null) return null;

        Budget budget = new Budget();

        budget.setDate(dto.getDate());
        budget.setDateReference(dto.getDateReference());
        budget.setCustomer(customer);
        budget.setProduct(productList);
        budget.setTaxes(dto.getTaxes());
        budget.setFreight(dto.getFreight());
        budget.setInstallation(dto.getInstallation());
        budget.setManagement(dto.getManagement());
        budget.setFixedExpenses(dto.getFixedExpenses());

        return budget;
    }

    public Budget updateFromDTO(
            BudgetUpdateDTO dto,
            Budget budget,
            Customer customer,
            List<Product> productList
    ) {
        if (dto == null) return null;

        budget.setDate(dto.getDate());
        budget.setDateReference(dto.getDateReference());
        budget.setCustomer(customer);
        budget.setProduct(productList);
        budget.setTaxes(dto.getTaxes());
        budget.setFreight(dto.getFreight());
        budget.setInstallation(dto.getInstallation());
        budget.setManagement(dto.getManagement());
        budget.setFixedExpenses(dto.getFixedExpenses());

        return budget;
    }

    public BudgetResponseDTO toResponseDTO(Budget budget) {

        if (budget == null) return null;

        BudgetResponseDTO dto = new BudgetResponseDTO();

        dto.setId(budget.getId());
        dto.setDate(budget.getDate());
        dto.setDateReference(budget.getDateReference());
        dto.setCustomer(customerMapper.toResponseDTO(budget.getCustomer()));
        dto.setProducts(
                budget.getProduct()
                        .stream()
                        .map(productMapper::toResponseDTO)
                        .collect(Collectors.toList()));

        dto.setTaxes(budget.getTaxes());
        dto.setFreight(budget.getFreight());
        dto.setInstallation(budget.getInstallation());
        dto.setManagement(budget.getManagement());
        dto.setFixedExpenses(budget.getFixedExpenses());
        dto.setTotal(budget.getTotalCost());

        return dto;
    }
}
