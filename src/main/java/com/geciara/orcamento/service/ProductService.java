package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.ProductRequestDTO;
import com.geciara.orcamento.dto.ProductResponseDTO;
import com.geciara.orcamento.dto.ProductUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.ProductMapper;
import com.geciara.orcamento.model.entitys.Budget;
import com.geciara.orcamento.model.entitys.Product;
import com.geciara.orcamento.model.entitys.costDetails.Taxes;
import com.geciara.orcamento.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository,
                          ProductMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public ProductResponseDTO save(ProductRequestDTO dto, Budget budget) {
        Product product = mapper.toEntity(dto, budget);
        updateCostAndPrice(product, LocalDate.now());
        product = repository.save(product);
        return mapper.toResponseDTO(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Produto não encontrado"));
        return mapper.toResponseDTO(product);
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductUpdateDTO dto, Budget budget) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Produto não encontrado"));
        Product updatedProduct = mapper.updateFromDTO(dto, product, budget);
        updateCostAndPrice(updatedProduct, LocalDate.now());
        repository.save(updatedProduct);
        return mapper.toResponseDTO(updatedProduct);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ItemNotFoundException("Produto não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Product findProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Produto não encontrado"));
    }

    public BigDecimal calculateCost(Product product, LocalDate baseDate) {
        return product.getItems().stream()
                .map(mc -> Optional.ofNullable(mc.getCost(baseDate)).orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateCostAndPrice(Product product, LocalDate baseDate) {
        product.setCost(calculateCost(product, baseDate));

        Taxes taxes = product.getBudget().getTaxes();
        product.setTaxes(taxes != null ? new Taxes(taxes) : new Taxes());
        product.setPrice(taxes != null ? product.getCost().add(taxes.getTotal(product.getCost())) : product.getCost());
    }
}
