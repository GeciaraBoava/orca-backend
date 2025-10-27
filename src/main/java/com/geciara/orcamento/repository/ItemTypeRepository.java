package com.geciara.orcamento.repository;

import com.geciara.orcamento.model.entitys.registerDetails.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
    Optional<ItemType> findByDescription(String description);
}
