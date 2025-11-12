package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.BudgetRequestDTO;
import com.geciara.orcamento.dto.BudgetResponseDTO;
import com.geciara.orcamento.dto.BudgetUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.BudgetMapper;
import com.geciara.orcamento.model.entitys.Budget;
import com.geciara.orcamento.model.entitys.Customer;
import com.geciara.orcamento.model.entitys.Product;
import com.geciara.orcamento.repository.BudgetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final CustomerService customerService;
    private final ProductService productService;

    public BudgetService(BudgetRepository budgetRepository,
                         BudgetMapper budgetMapper,
                         CustomerService customerService,
                         ProductService productService
    ) {
        this.budgetRepository = budgetRepository;
        this.budgetMapper = budgetMapper;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    public BudgetResponseDTO save(BudgetRequestDTO dto) {
        Customer customer = customerService.findEntityById(dto.getCustomerId());

        List<Product> products = dto.getProductIds().stream()
                .map(productService::findProductById)
                .collect(Collectors.toList());

        Budget budget = budgetMapper.toEntity(dto, customer, products);

        for (Product product : products) {
            product.setBudget(budget);
        }

        budget.setProducts(products);
        calculateTotals(budget);

        budget = budgetRepository.save(budget);
        return budgetMapper.toResponseDTO(budget);
    }

    @Transactional(readOnly = true)
    public List<BudgetResponseDTO> listAll() {
        return budgetRepository.findAll()
                .stream()
                .map(budgetMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BudgetResponseDTO findById(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Orçamento não encontrado"));
        return budgetMapper.toResponseDTO(budget);
    }

    @Transactional(readOnly = true)
    public Budget findEntityById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found: " + id));
    }

    @Transactional
    public BudgetResponseDTO update(Long id, BudgetUpdateDTO dto) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Orçamento não encontrado"));

        List<Product> products;
        if(dto.getProductIds() != null && !dto.getProductIds().isEmpty()) {
            products = dto.getProductIds()
                    .stream()
                    .map(productService::findProductById)
                    .toList();

            products.forEach(product -> product.setBudget(budget));

            budget.getProducts().clear();
            budget.getProducts().addAll(products);
        } else {
            products = budget.getProducts();
        }

        Budget updatedBudget = budgetMapper.updateFromDTO(dto, budget, products);

        LocalDate dateReference = budget.getDateReference() != null
                ? budget.getDateReference()
                : budget.getDate();

        products.forEach(product ->
                productService.updateCostAndPrice(product, dateReference)
        );

        calculateTotals(updatedBudget);

        updatedBudget = budgetRepository.save(updatedBudget);
        return budgetMapper.toResponseDTO(updatedBudget);
    }

    private void calculateTotals(Budget budget) {

        budget.setTotalCost(budget.getProducts()
                .stream()
                .map(Product::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        budget.setTotalPrice(budget.getProducts()
                .stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    @Transactional
    public void delete(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new ItemNotFoundException("Orçamento não encontrado");
        }
        budgetRepository.deleteById(id);
    }
}
