package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.MaterialTypeRequestDTO;
import com.geciara.orcamento.dto.MaterialTypeResponseDTO;
import com.geciara.orcamento.dto.MaterialTypeUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.MaterialTypeMapper;
import com.geciara.orcamento.model.entitys.registerDetails.MaterialType;
import com.geciara.orcamento.repository.MaterialTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MaterialTypeService {

    private final MaterialTypeRepository materialTypeRepository;
    private final MaterialTypeMapper materialTypeMapper;

    public MaterialTypeService(MaterialTypeRepository materialTypeRepository, MaterialTypeMapper materialTypeMapper) {
        this.materialTypeRepository = materialTypeRepository;
        this.materialTypeMapper = materialTypeMapper;
    }
    public MaterialTypeResponseDTO save(MaterialTypeRequestDTO dto) {
        MaterialType materialType = materialTypeMapper.toEntity(dto);
        materialType = materialTypeRepository.save(materialType);
        return materialTypeMapper.toResponseDTO(materialType);
    }

    public List<MaterialTypeResponseDTO> listAll() {
        return materialTypeRepository.findAll()
                .stream()
                .map(materialTypeMapper::toResponseDTO)
                .toList();
    }

    public MaterialTypeResponseDTO findById(Long id) {
        MaterialType materialType = materialTypeRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        return materialTypeMapper.toResponseDTO(materialType);
    }

    public MaterialType findMaterialTypeById(Long id) {
        return materialTypeRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    public MaterialTypeResponseDTO update(Long id, MaterialTypeUpdateDTO dto) {
        MaterialType materialType = materialTypeRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        MaterialType updatedMaterialType = materialTypeMapper.updateFromDTO(dto, materialType);
        materialTypeRepository.save(updatedMaterialType);
        return materialTypeMapper.toResponseDTO(updatedMaterialType);
    }

    public void delete(Long id) {
        if (!materialTypeRepository.existsById(id)) {
            throw new ItemNotFoundException();
        }
        materialTypeRepository.deleteById(id);
    }


}