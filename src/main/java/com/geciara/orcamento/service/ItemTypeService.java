package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.ItemTypeRequestDTO;
import com.geciara.orcamento.dto.ItemTypeResponseDTO;
import com.geciara.orcamento.dto.ItemTypeUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.ItemTypeMapper;
import com.geciara.orcamento.model.entitys.registerDetails.ItemType;
import com.geciara.orcamento.repository.ItemTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTypeService {

    private final ItemTypeRepository itemTypeRepository;
    private final ItemTypeMapper itemTypeMapper;

    public ItemTypeService(ItemTypeRepository itemTypeRepository, ItemTypeMapper itemTypeMapper) {
        this.itemTypeRepository = itemTypeRepository;
        this.itemTypeMapper = itemTypeMapper;
    }

    public ItemTypeResponseDTO save(ItemTypeRequestDTO dto) {
        ItemType itemType = itemTypeMapper.toEntity(dto);
        itemType = itemTypeRepository.save(itemType);
        return itemTypeMapper.toResponseDTO(itemType);
    }

    public List<ItemTypeResponseDTO> listAll() {
        return itemTypeRepository.findAll()
                .stream()
                .map(itemTypeMapper::toResponseDTO)
                .toList();
    }

    public ItemTypeResponseDTO findById(Long id) {
        ItemType itemType = itemTypeRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        return itemTypeMapper.toResponseDTO(itemType);
    }

    public ItemType findItemtypeById(Long id) {
        return itemTypeRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    public ItemTypeResponseDTO update(Long id, ItemTypeUpdateDTO dto) {
        ItemType itemType = itemTypeRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        ItemType updatedEntity = itemTypeMapper.updateFromDTO(dto, itemType);
        itemTypeRepository.save(updatedEntity);
        return itemTypeMapper.toResponseDTO(updatedEntity);
    }

    public void delete(Long id) {
        if (!itemTypeRepository.existsById(id)) {
            throw new ItemNotFoundException();
        }
        itemTypeRepository.deleteById(id);
    }
}
