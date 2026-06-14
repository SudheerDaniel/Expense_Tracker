package com.example.springbootmvcexample.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootmvcexample.model.Budget;
import com.example.springbootmvcexample.service.BudgetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    // set or update budget for a given month and year
    @PutMapping
    public ResponseEntity<Budget> setBudget(
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam double budgetAmount) {
        Budget budget = budgetService.setBudget(month, year, budgetAmount);
        return ResponseEntity.ok(budget);
    }

    // get budget for a given month and year
    @GetMapping
    public ResponseEntity<Budget> getBudget(
            @RequestParam int month,
            @RequestParam int year) {
        Optional<Budget> budget = budgetService.getBudget(month, year);
        // return 200 with budget if found, 204 no content if not set
        return budget.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    // delete budget for a given month and year
   @DeleteMapping
   public ResponseEntity<Void> deleteBudget(
           @RequestParam int month,
           @RequestParam int year) {
       budgetService.deleteBudget(month, year);
       return ResponseEntity.noContent().build();
   }
}
