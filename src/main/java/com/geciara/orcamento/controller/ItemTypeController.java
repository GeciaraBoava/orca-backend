package com.geciara.orcamento.controller;

import com.geciara.orcamento.dto.ItemTypeRequestDTO;
import com.geciara.orcamento.dto.ItemTypeResponseDTO;
import com.geciara.orcamento.dto.ItemTypeUpdateDTO;
import com.geciara.orcamento.service.ItemTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tipos de itens", description = "Gerenciamento de tipos de itens do sistema")
@RestController
@RequestMapping("/config/items-types")
@Validated
public class ItemTypeController {

    private final ItemTypeService itemTypeService;

    public ItemTypeController(ItemTypeService itemTypeService) {
        this.itemTypeService = itemTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ItemTypeResponseDTO>> listAll() {
        return ResponseEntity.ok(itemTypeService.listAll());
    }

    @Operation(summary = "Buscar tipo de item por ID", description = "Recupera um tipo de item  pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Tipo de item  encontrado")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "Tipo de item  não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ItemTypeResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(itemTypeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ItemTypeResponseDTO> save(@RequestBody @Valid ItemTypeRequestDTO entity) {
        ItemTypeResponseDTO savedEntity = itemTypeService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemTypeResponseDTO> update(@PathVariable Long id,@RequestBody @Valid ItemTypeUpdateDTO entity) {
        ItemTypeResponseDTO updatedEntity = itemTypeService.update(id, entity);
        return ResponseEntity.ok(updatedEntity);

    }


}