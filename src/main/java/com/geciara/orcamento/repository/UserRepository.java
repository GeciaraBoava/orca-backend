package com.geciara.orcamento.repository;

import com.geciara.orcamento.model.entitys.User;
import com.geciara.orcamento.model.enums.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findByRole(EUserRole role);
    List<User> findByActiveTrue();
    List<User> findByActiveFalse();
}