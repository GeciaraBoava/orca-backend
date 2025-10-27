package com.geciara.orcamento.repository;

import com.geciara.orcamento.model.entitys.registerDetails.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialTypeRepository extends JpaRepository<MaterialType, Long> {

    Optional<MaterialType> findByDescription(String description);
}

