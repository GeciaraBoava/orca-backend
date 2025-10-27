package com.geciara.orcamento.repository;

import com.geciara.orcamento.model.entitys.registerDetails.UnitMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitMeasureRepository extends JpaRepository<UnitMeasure, Long> {

    Optional<UnitMeasure> findByDescription(String description);


}