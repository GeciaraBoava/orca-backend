package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.ProductRequestDTO;
import com.geciara.orcamento.dto.ProductResponseDTO;
import com.geciara.orcamento.dto.ProductUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.ProductMapper;
import com.geciara.orcamento.model.entitys.Product;
import com.geciara.orcamento.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public ProductResponseDTO save(ProductRequestDTO dto) {
        Product product = mapper.toEntity(dto);
        // Atualiza custo e preço
        product.updateCostAndPrice(product.getBudget(), LocalDate.now());
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
    public ProductResponseDTO update(Long id, ProductUpdateDTO dto) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Produto não encontrado"));
        Product updatedProduct = mapper.updateFromDTO(dto, product);
        updatedProduct.updateCostAndPrice(updatedProduct.getBudget(), LocalDate.now());
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
}
