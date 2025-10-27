package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.CompositionRequestDTO;
import com.geciara.orcamento.dto.CompositionResponseDTO;
import com.geciara.orcamento.dto.CompositionUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.CompositionMapper;
import com.geciara.orcamento.model.entitys.Composition;
import com.geciara.orcamento.model.entitys.Material;
import com.geciara.orcamento.model.entitys.registerDetails.ItemType;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
import com.geciara.orcamento.repository.CompositionRepository;
import org.springframework.stereotype.Service;

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

    public CompositionResponseDTO save(CompositionRequestDTO dto) {
        ItemType type = itemTypeService.findItemtypeById(dto.getTypeId());
        UnitMeasure unit = unitMeasureService.findUnitMeasureById(dto.getUnitMeasureId());
        Material material = materialService.findMaterialById(dto.getMaterialId());

        Composition composition = compositionMapper.toEntity(dto, type, unit, material);
        composition = compositionRepository.save(composition);

        return compositionMapper.toResponseDTO(composition);
    }

    public List<CompositionResponseDTO> listAll() {
        return compositionRepository.findAll()
                .stream()
                .map(compositionMapper::toResponseDTO)
                .toList();
    }

    public CompositionResponseDTO findById(Long id) {
        Composition composition = compositionRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        return compositionMapper.toResponseDTO(composition);
    }

    public Composition findProductItemById(Long id) {
        return compositionRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    public CompositionResponseDTO update(Long id, CompositionUpdateDTO dto) {
        Composition composition = compositionRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        Composition updatedComposition = compositionMapper.updateFromDTO(dto, composition);
        compositionRepository.save(updatedComposition);
        return compositionMapper.toResponseDTO(updatedComposition);
    }

    public void delete(Long id) {
        if (!compositionRepository.existsById(id)) {
            throw new ItemNotFoundException();
        }
        compositionRepository.deleteById(id);
    }

}
