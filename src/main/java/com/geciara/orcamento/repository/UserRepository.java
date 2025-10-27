package com.geciara.orcamento.repository;

import com.geciara.orcamento.model.entitys.User;
import com.geciara.orcamento.model.enums.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByUsername(String username);
    List<User> findByRole(EUserRole role);
    boolean existsByCadastroEmail(String email);

}
