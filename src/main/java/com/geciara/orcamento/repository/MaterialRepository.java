package com.geciara.orcamento.repository;

import com.geciara.orcamento.model.entitys.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findByDescription(String description);
}

