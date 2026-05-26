package com.example.springbootmvcexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.example.springbootmvcexample.service.ExpenseTrackerService;
import com.example.springbootmvcexample.model.ExpenseTracker;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    ExpenseTrackerService expenseService;

    @GetMapping
    public List<ExpenseTracker> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PostMapping
    public void addExpense(@RequestBody ExpenseTracker expense) {
        expenseService.addExpenses(expense);
    }

    @PutMapping("/item/{itemDescription}")
    public ExpenseTracker updateExpenseByItemDescription(@PathVariable String itemDescription,@RequestBody ExpenseTracker updatedExpense) {
       return  expenseService.updateExpenseByItemDescription(itemDescription, updatedExpense);
    } 
    @PutMapping("/{id}")
    public ExpenseTracker updateExpense(@PathVariable int id,@RequestBody ExpenseTracker updatedExpense){
        return expenseService.updateExpense(id, updatedExpense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable int id) {
        expenseService.deleteExpense(id);
    }
    @DeleteMapping("/category/{category}")
    public void deleteExpenseByCategory(@PathVariable String category) {
        expenseService.deleteExpenseByCategory(category);
    }
    @GetMapping("/category/{category}")
    public List<ExpenseTracker> getExpenseByCategory(@PathVariable String category) {
       return expenseService.getExpenseByCategory(category);
    }
    @GetMapping("/item/{itemDescription}")
    public List<ExpenseTracker> getExpenseByItemDescription(@PathVariable String itemDescription) {
        return expenseService.getExpenseByItemDescription(itemDescription);
    }
    @GetMapping("/subcategory/{subCategory}")
    public List<ExpenseTracker> getExpenseBySubCategory(@PathVariable String subCategory) {
        return expenseService.getExpenseBySubCategory(subCategory);
    }
    @GetMapping("/merchant/{merchant}")
    public List<ExpenseTracker> getExpensesByMerchant(@PathVariable String merchant) {
        return expenseService.getExpensesByMerchant(merchant);
    }
    @GetMapping("/paymentmethod/{paymentMethod}")
    public List<ExpenseTracker> getExpensesByPaymentMethod(@PathVariable String paymentMethod) {
        return expenseService.getExpensesByPaymentMethod(paymentMethod);
    }
    @GetMapping("/notes/{notes}")
    public List<ExpenseTracker> getExpensesByNotes(@PathVariable String notes) {
        return expenseService.getExpensesByNotes(notes);
    }
   


}