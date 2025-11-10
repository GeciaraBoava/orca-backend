package com.geciara.orcamento.service;

import com.geciara.orcamento.dto.BudgetRequestDTO;
import com.geciara.orcamento.dto.BudgetResponseDTO;
import com.geciara.orcamento.dto.BudgetUpdateDTO;
import com.geciara.orcamento.exceptions.ItemNotFoundException;
import com.geciara.orcamento.mapper.BudgetMapper;
import com.geciara.orcamento.model.entitys.Budget;
import com.geciara.orcamento.repository.BudgetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public BudgetService(BudgetRepository budgetRepository, BudgetMapper budgetMapper) {
        this.budgetRepository = budgetRepository;
        this.budgetMapper = budgetMapper;
    }

    @Transactional
    public BudgetResponseDTO save(BudgetRequestDTO dto) {
        Budget budget = budgetMapper.toEntity(dto);

        // Salva orçamento e retorna DTO
        budget = budgetRepository.save(budget);
        return budgetMapper.toResponseDTO(budget);
    }

    @Transactional(readOnly = true)
    public List<BudgetResponseDTO> listAll() {
        return budgetRepository.findAll()
                .stream()
                .map(budgetMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BudgetResponseDTO findById(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Orçamento não encontrado"));
        return budgetMapper.toResponseDTO(budget);
    }

    @Transactional(readOnly = true)
    public Budget findEntityById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found: " + id));
    }

    @Transactional
    public BudgetResponseDTO update(Long id, BudgetUpdateDTO dto) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Orçamento não encontrado"));

        Budget updatedBudget = budgetMapper.updateFromDTO(dto, budget);

        // Salva orçamento atualizado
        updatedBudget = budgetRepository.save(updatedBudget);
        return budgetMapper.toResponseDTO(updatedBudget);
    }

    @Transactional
    public void delete(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new ItemNotFoundException("Orçamento não encontrado");
        }
        budgetRepository.deleteById(id);
    }
}
