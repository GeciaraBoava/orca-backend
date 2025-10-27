package com.geciara.orcamento.repository;

import com.geciara.orcamento.model.entitys.Budget;
import com.geciara.orcamento.model.entitys.Customer;
import com.geciara.orcamento.model.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    UserDetails findByCustomer(Customer customer);
    List<User> findByDate(LocalDate date);

}
