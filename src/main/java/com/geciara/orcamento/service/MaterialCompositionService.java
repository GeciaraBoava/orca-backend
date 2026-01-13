package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.ItemCompositionDTO;
import com.geciara.orcamento.dto.MaterialCompositionResponseDTO;
import com.geciara.orcamento.dto.MaterialCompositionUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.ItemCompositionMapper;
import com.geciara.orcamento.model.entitys.ItemComposition;
import com.geciara.orcamento.model.enums.ETypeMaterialComposition;
import com.geciara.orcamento.repository.MaterialCompositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialCompositionService {

    private final MaterialCompositionRepository repository;
    private final ItemCompositionMapper mapper;
    private final MaterialService materialService;
    private final CompositionService compositionService;

    public MaterialCompositionService(MaterialCompositionRepository repository,
                                      ItemCompositionMapper mapper,
                                      MaterialService materialService,
                                      CompositionService compositionService) {
        this.repository = repository;
        this.mapper = mapper;
        this.materialService = materialService;
        this.compositionService = compositionService;
    }

    @Transactional
    public MaterialCompositionResponseDTO save(ItemCompositionDTO dto) {
        ItemComposition mc = mapper.toEntity(dto);
        setMaterialOrComposition(mc, dto);

        // Opcional: calcular custo inicial
        mc.setCost(mc.getCost(java.time.LocalDate.now()));

        mc = repository.save(mc);
        return mapper.toResponseDTO(mc);
    }

    @Transactional(readOnly = true)
    public List<MaterialCompositionResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public MaterialCompositionResponseDTO update(Long id, MaterialCompositionUpdateDTO dto) {
        ItemComposition mc = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("ItemComposition não encontrado"));

        mc = mapper.updateFromDTO(dto, mc);
        mc.setCost(mc.getCost(java.time.LocalDate.now()));
        repository.save(mc);

        return mapper.toResponseDTO(mc);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ItemNotFoundException("ItemComposition não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ItemComposition findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("ItemComposition não encontrado"));
    }

    // --------------------------
    // Métodos privados
    // --------------------------
    private void setMaterialOrComposition(ItemComposition mc, ItemCompositionDTO dto) {
        if (dto.getType() == ETypeMaterialComposition.MATERIAL) {
            mc.setMaterial(materialService.findMaterialById(dto.getMaterialId()));
        } else if (dto.getType() == ETypeMaterialComposition.COMPOSITION) {
            mc.setComposition(compositionService.findCompositionById(dto.getCompositionId()));
        } else {
            throw new IllegalArgumentException("Tipo de ItemComposition inválido");
        }
    }
}
