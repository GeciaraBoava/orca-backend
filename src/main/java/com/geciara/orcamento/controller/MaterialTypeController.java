package com.geciara.orcamento.controller;

import com.geciara.orcamento.dto.MaterialTypeRequestDTO;
import com.geciara.orcamento.dto.MaterialTypeResponseDTO;
import com.geciara.orcamento.dto.MaterialTypeUpdateDTO;
import com.geciara.orcamento.service.MaterialTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config/material-types")
@Validated
public class MaterialTypeController {

    private final MaterialTypeService materialTypeService;

    public MaterialTypeController(MaterialTypeService materialTypeService) {
        this.materialTypeService = materialTypeService;
    }

    @GetMapping
    public ResponseEntity<List<MaterialTypeResponseDTO>> listAll() {
        return ResponseEntity.ok(materialTypeService.listAll());
    }

    @Operation(summary = "Buscar tipo de insumo por ID", description = "Recupera um tipo de insumo pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Tipo de insumo encontrado")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "tipo de insumo não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<MaterialTypeResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(materialTypeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MaterialTypeResponseDTO> save(@RequestBody @Valid MaterialTypeRequestDTO entity) {
        MaterialTypeResponseDTO savedEntity = materialTypeService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        materialTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialTypeResponseDTO> update(@PathVariable Long id, @RequestBody @Valid MaterialTypeUpdateDTO entity) {
        MaterialTypeResponseDTO updatedEntity = materialTypeService.update(id, entity);
        return ResponseEntity.ok(updatedEntity);

    }


}
