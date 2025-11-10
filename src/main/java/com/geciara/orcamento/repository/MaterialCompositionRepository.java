package com.geciara.orcamento.repository;

import com.geciara.orcamento.model.entitys.ItemComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialCompositionRepository extends JpaRepository<ItemComposition, Long> {
    Optional<ItemComposition> findByDescription(String description);
}

