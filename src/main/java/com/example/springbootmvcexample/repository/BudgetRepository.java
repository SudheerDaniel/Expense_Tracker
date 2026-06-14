package com.example.springbootmvcexample.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootmvcexample.model.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {


    // find budget for a specific user, month and year
    Optional<Budget> findByUserIdAndMonthAndYear(Long userId, int month, int year);

    // delete budget for a specific user, month and year
    @Transactional
    void deleteByUserIdAndMonthAndYear(Long userId, int month, int year);

}
