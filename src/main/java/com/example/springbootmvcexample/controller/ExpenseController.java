package com.example.springbootmvcexample.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import jakarta.validation.Valid;

import com.example.springbootmvcexample.dto.ExpenseSummaryDTO;
import com.example.springbootmvcexample.model.ExpenseTracker;
import com.example.springbootmvcexample.service.ExpenseTrackerService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseTrackerService expenseService;

    public ExpenseController(ExpenseTrackerService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseTracker>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @PostMapping
    public ResponseEntity<Void> addExpense(@Valid @RequestBody ExpenseTracker expense) {
        expenseService.addExpenses(expense);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/item/{itemDescription}")
    public ResponseEntity<ExpenseTracker> updateExpenseByItemDescription(@PathVariable String itemDescription,@Valid @RequestBody ExpenseTracker updatedExpense) {
        return ResponseEntity.ok(expenseService.updateExpenseByItemDescription(itemDescription, updatedExpense));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseTracker> updateExpense(@PathVariable Long id,@Valid @RequestBody ExpenseTracker updatedExpense) {
        return ResponseEntity.ok(expenseService.updateExpense(id, updatedExpense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/category/{category}")
    public ResponseEntity<Void> deleteExpenseByCategory(@PathVariable String category) {
        expenseService.deleteExpenseByCategory(category);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExpenseTracker>> getExpenseByCategory(@PathVariable String category) {
        return ResponseEntity.ok(expenseService.getExpenseByCategory(category));
    }

    @GetMapping("/item/{itemDescription}")
    public ResponseEntity<List<ExpenseTracker>> getExpenseByItemDescription(@PathVariable String itemDescription) {
        return ResponseEntity.ok(expenseService.getExpenseByItemDescription(itemDescription));
    }

    @GetMapping("/subcategory/{subCategory}")
    public ResponseEntity<List<ExpenseTracker>> getExpenseBySubCategory(@PathVariable String subCategory) {
        return ResponseEntity.ok(expenseService.getExpenseBySubCategory(subCategory));
    }

    @GetMapping("/merchant/{merchant}")
    public ResponseEntity<List<ExpenseTracker>> getExpensesByMerchant(@PathVariable String merchant) {
        return ResponseEntity.ok(expenseService.getExpensesByMerchant(merchant));
    }

    @GetMapping("/paymentmethod/{paymentMethod}")
    public ResponseEntity<List<ExpenseTracker>> getExpensesByPaymentMethod(@PathVariable String paymentMethod) {
        return ResponseEntity.ok(expenseService.getExpensesByPaymentMethod(paymentMethod));
    }

    @GetMapping("/notes")
    public ResponseEntity<List<ExpenseTracker>> getExpensesByNotes(@RequestParam String notes) {
        return ResponseEntity.ok(expenseService.getExpensesByNotes(notes));
    }

    // return total spent, breakdown by category, and breakdown by payment method
    // for the given date range
    @GetMapping("/summary")
    public ResponseEntity<ExpenseSummaryDTO> getExpenseSummary(
            @RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to) {
        return ResponseEntity.ok(expenseService.getExpenseSummary(from,to));
    }
}
