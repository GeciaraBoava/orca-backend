package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.MaterialRequestDTO;
import com.geciara.orcamento.dto.MaterialResponseDTO;
import com.geciara.orcamento.dto.MaterialUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.MaterialMapper;
import com.geciara.orcamento.model.entitys.Material;
import com.geciara.orcamento.repository.MaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;

    public MaterialService(MaterialRepository materialRepository, MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
    }

    @Transactional
    public MaterialResponseDTO save(MaterialRequestDTO dto) {
        Material material = materialMapper.toEntity(dto);
        Material saved = materialRepository.save(material);
        return materialMapper.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<MaterialResponseDTO> listAll() {
        return materialRepository.findAll()
                .stream()
                .map(materialMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public MaterialResponseDTO findById(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Material não encontrado"));
        return materialMapper.toResponseDTO(material);
    }

    @Transactional
    public MaterialResponseDTO update(Long id, MaterialUpdateDTO dto) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Material não encontrado"));
        Material updated = materialMapper.updateFromDTO(dto, material);
        materialRepository.save(updated);
        return materialMapper.toResponseDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!materialRepository.existsById(id)) {
            throw new ItemNotFoundException("Material não encontrado");
        }
        materialRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Material findMaterialById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Material não encontrado"));
    }
}
