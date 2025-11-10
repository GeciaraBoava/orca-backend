package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.CompositionRequestDTO;
import com.geciara.orcamento.dto.CompositionResponseDTO;
import com.geciara.orcamento.dto.CompositionUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.CompositionMapper;
import com.geciara.orcamento.model.entitys.Composition;
import com.geciara.orcamento.model.entitys.registerDetails.ItemType;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
import com.geciara.orcamento.repository.CompositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompositionService {

    private final CompositionRepository compositionRepository;
    private final CompositionMapper compositionMapper;
    private final ItemTypeService itemTypeService;
    private final UnitMeasureService unitMeasureService;
    private final MaterialService materialService;

    public CompositionService(
            CompositionRepository compositionRepository,
            CompositionMapper compositionMapper,
            ItemTypeService itemTypeService,
            UnitMeasureService unitMeasureService,
            MaterialService materialService
    ) {
        this.compositionRepository = compositionRepository;
        this.compositionMapper = compositionMapper;
        this.itemTypeService = itemTypeService;
        this.unitMeasureService = unitMeasureService;
        this.materialService = materialService;
    }

    @Transactional
    public CompositionResponseDTO save(CompositionRequestDTO dto) {
        Composition composition = compositionMapper.toEntity(dto);

        ItemType type = itemTypeService.findItemtypeById(dto.getTypeId());
        UnitMeasure unit = unitMeasureService.findUnitMeasureById(dto.getUnitMeasureId());
        composition.setType(type);
        composition.setUnitMeasure(unit);

        if (dto.getMaterialComposition() != null) {
            dto.getMaterialComposition().forEach(mc -> {
                mc.setMaterial(materialService.findMaterialById(mc.getMaterial().getId()));
                composition.addMaterialComposition(mc);
            });
        }

        Composition saved = compositionRepository.save(composition);
        return compositionMapper.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<CompositionResponseDTO> listAll() {
        return compositionRepository.findAll()
                .stream()
                .map(compositionMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CompositionResponseDTO findById(Long id) {
        Composition composition = compositionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Composição não encontrada"));
        return compositionMapper.toResponseDTO(composition);
    }

    @Transactional
    public CompositionResponseDTO update(Long id, CompositionUpdateDTO dto) {
        Composition composition = compositionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Composição não encontrada"));

        Composition updated = compositionMapper.updateFromDTO(dto, composition);
        if (dto.getTypeId() != null) {
            updated.setType(itemTypeService.findItemtypeById(dto.getTypeId()));
        }
        if (dto.getUnitMeasureId() != null) {
            updated.setUnitMeasure(unitMeasureService.findUnitMeasureById(dto.getUnitMeasureId()));
        }

        compositionRepository.save(updated);
        return compositionMapper.toResponseDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!compositionRepository.existsById(id)) {
            throw new ItemNotFoundException("Composição não encontrada");
        }
        compositionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Composition findCompositionById(Long id) {
        return compositionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Composição não encontrada"));
    }
}
