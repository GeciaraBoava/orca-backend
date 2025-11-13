package com.geciara.orcamento.controller;

import com.geciara.orcamento.dto.UnitMeasureRequestDTO;
import com.geciara.orcamento.dto.UnitMeasureResponseDTO;
import com.geciara.orcamento.dto.UnitMeasureUpdateDTO;
import com.geciara.orcamento.service.UnitMeasureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Unidades de medida", description = "Gerenciamento de unidades de medida do sistema")
@RestController
@RequestMapping("/config/unit-measures")
@Validated
public class UnitMeasureController {

    private final UnitMeasureService unitMeasureService;

    public UnitMeasureController(UnitMeasureService unitMeasureService) {
        this.unitMeasureService = unitMeasureService;
    }

    @GetMapping
    public ResponseEntity<List<UnitMeasureResponseDTO>> listAll() {
        return ResponseEntity.ok(unitMeasureService.listAll());
    }

    @Operation(summary = "Buscar unidade de medida por ID", description = "Recupera um unidade de medida pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Unidade De Medida encontrado")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "Unidade De Medida não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<UnitMeasureResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(unitMeasureService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UnitMeasureResponseDTO> save(@RequestBody @Valid UnitMeasureRequestDTO entity) {
        UnitMeasureResponseDTO savedEntity = unitMeasureService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        unitMeasureService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitMeasureResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UnitMeasureUpdateDTO entity) {
        UnitMeasureResponseDTO updatedEntity = unitMeasureService.update(id, entity);
        return ResponseEntity.ok(updatedEntity);

    }


}