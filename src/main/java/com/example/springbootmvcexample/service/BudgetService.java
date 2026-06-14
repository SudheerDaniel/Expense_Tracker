package com.example.springbootmvcexample.service;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.springbootmvcexample.model.Budget;
import com.example.springbootmvcexample.model.User;
import com.example.springbootmvcexample.repository.BudgetRepository;
import com.example.springbootmvcexample.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetService {

   private final BudgetRepository budgetRepo;
   private final UserRepository userRepository;

   // set or update budget for a specific month and year
   public Budget setBudget(int month, int year, double budgetAmount) {
       User user = getCurrentUser();
       // check if budget already exists for this month/year - update it if so
       Optional<Budget> existing = budgetRepo.findByUserIdAndMonthAndYear(user.getId(), month, year);
       Budget budget = existing.orElse(new Budget());
       budget.setUserId(user.getId());
       budget.setMonth(month);
       budget.setYear(year);
       budget.setBudgetAmount(budgetAmount);
       return budgetRepo.save(budget);
   }

   // get budget for a specific month and year - returns empty if not set
   public Optional<Budget> getBudget(int month, int year) {
       User user = getCurrentUser();
       return budgetRepo.findByUserIdAndMonthAndYear(user.getId(), month, year);
   }

   // delete budget for a specific month and year
   public void deleteBudget(int month, int year) {
       User user = getCurrentUser();
       budgetRepo.deleteByUserIdAndMonthAndYear(user.getId(), month, year);
   }
 
   private User getCurrentUser() {
      String email = (String) SecurityContextHolder.getContext()
              .getAuthentication()
              .getPrincipal();
      return userRepository.findByEmail(email)
              .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
