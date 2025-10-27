package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.UnitMeasureRequestDTO;
import com.geciara.orcamento.dto.UnitMeasureResponseDTO;
import com.geciara.orcamento.dto.UnitMeasureUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.UnitMeasureMapper;
import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
import com.geciara.orcamento.repository.UnitMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UnitMeasureService {

    private final UnitMeasureRepository unitMeasureRepository;
    private final UnitMeasureMapper unitMeasureMapper;

    public UnitMeasureService(UnitMeasureRepository unitMeasureRepository, UnitMeasureMapper unitMeasureMapper) {
        this.unitMeasureRepository = unitMeasureRepository;
        this.unitMeasureMapper = unitMeasureMapper;
    }

    public UnitMeasureResponseDTO save(UnitMeasureRequestDTO dto) {
        UnitMeasure unitMeasure = unitMeasureMapper.toEntity(dto);
        unitMeasure = unitMeasureRepository.save(unitMeasure);
        return unitMeasureMapper.toResponseDTO(unitMeasure);
    }

    public List<UnitMeasureResponseDTO> listAll() {
        return unitMeasureRepository.findAll()
                .stream()
                .map(unitMeasureMapper::toResponseDTO)
                .toList();
    }

    public UnitMeasureResponseDTO findById(Long id) {
        UnitMeasure unitMeasure = unitMeasureRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        return unitMeasureMapper.toResponseDTO(unitMeasure);
    }

    public UnitMeasure findUnitMeasureById(Long id) {
        return unitMeasureRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    public UnitMeasureResponseDTO update(Long id, UnitMeasureUpdateDTO dto) {
        UnitMeasure unitMeasure = unitMeasureRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        UnitMeasure updatedUnitMeasure = unitMeasureMapper.updateFromDTO(dto, unitMeasure);
        unitMeasureRepository.save(updatedUnitMeasure);
        return unitMeasureMapper.toResponseDTO(updatedUnitMeasure);
    }

    public void delete(Long id) {
        if (!unitMeasureRepository.existsById(id)) {
            throw new ItemNotFoundException();
        }
        unitMeasureRepository.deleteById(id);
    }


}
