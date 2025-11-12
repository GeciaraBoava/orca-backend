package com.geciara.orcamento.controller;

import com.geciara.orcamento.dto.ProductRequestDTO;
import com.geciara.orcamento.dto.ProductResponseDTO;
import com.geciara.orcamento.dto.ProductUpdateDTO;
import com.geciara.orcamento.model.entitys.Budget;
import com.geciara.orcamento.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Produtos", description = "Gerenciamento de produtos do sistema")
@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listAll() {
        return ResponseEntity.ok(productService.listAll());
    }

    @Operation(summary = "Buscar produto por ID", description = "Recupera um produto pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Produto encontrado")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> save(@RequestBody @Valid ProductRequestDTO entity, Budget budget) {
        ProductResponseDTO savedEntity = productService.save(entity, budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id,@RequestBody @Valid ProductUpdateDTO entity, Budget budget) {
        ProductResponseDTO updatedEntity = productService.update(id, entity, budget);
        return ResponseEntity.ok(updatedEntity);

    }


}