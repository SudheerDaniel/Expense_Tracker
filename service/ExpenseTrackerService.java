package com.example.springbootmvcexample.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootmvcexample.exception.ExpenseNotFoundException;
import com.example.springbootmvcexample.model.ExpenseTracker;
import com.example.springbootmvcexample.model.User;
import com.example.springbootmvcexample.repository.ExpenseTrackerRepository;
import com.example.springbootmvcexample.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseTrackerService {
     private final UserRepository userRepository;
     
   private final ExpenseTrackerRepository expenseRepo;

   public List<ExpenseTracker> getAllExpenses() {
     String email = getCurrentUserEmail();
     User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
                return expenseRepo.findByUserId(user.getId());
   }
   public List<ExpenseTracker> getExpenseByCategory(String category) {
         String email = getCurrentUserEmail();
         User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
         return expenseRepo.findByUserIdAndCategory(user.getId(), category);
   }
   public void addExpenses(ExpenseTracker expense) {
    String email = getCurrentUserEmail();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    expense.setUserId(user.getId());
    expenseRepo.save(expense);
}

public List<ExpenseTracker> getExpenseByItemDescription(String itemDescription) {
    String email = getCurrentUserEmail();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return expenseRepo.findByUserIdAndItemDescription(user.getId(), itemDescription);
}

public List<ExpenseTracker> getExpenseBySubCategory(String subCategory) {
    String email = getCurrentUserEmail();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return expenseRepo.findByUserIdAndSubCategory(user.getId(), subCategory);
}

public List<ExpenseTracker> getExpensesByMerchant(String merchant) {
    String email = getCurrentUserEmail();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return expenseRepo.findByUserIdAndMerchant(user.getId(), merchant);
}

public List<ExpenseTracker> getExpensesByPaymentMethod(String paymentMethod) {
    String email = getCurrentUserEmail();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return expenseRepo.findByUserIdAndPaymentMethod(user.getId(), paymentMethod);
}

public List<ExpenseTracker> getExpensesByNotes(String notes) {
    String email = getCurrentUserEmail();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return expenseRepo.findByUserIdAndNotesContainingIgnoreCase(user.getId(), notes);
}

public void deleteExpense(Long id) {
    String email = getCurrentUserEmail();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    expenseRepo.deleteByUserIdAndId(user.getId(), id);
}

@Transactional
public void deleteExpenseByCategory(String category) {
    String email = getCurrentUserEmail();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    expenseRepo.deleteByUserIdAndCategory(user.getId(), category);
}

@Transactional
public ExpenseTracker updateExpense(Long id, ExpenseTracker updatedExpense) {
    String email = getCurrentUserEmail();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return expenseRepo.findById(id)
            .filter(expense -> expense.getUserId().equals(user.getId()))
            .map(expense -> {
                expense.setItemDescription(updatedExpense.getItemDescription());
                expense.setDate(updatedExpense.getDate());
                expense.setAmount(updatedExpense.getAmount());
                expense.setCategory(updatedExpense.getCategory());
                expense.setSubCategory(updatedExpense.getSubCategory());
                expense.setPaymentMethod(updatedExpense.getPaymentMethod());
                expense.setReceiptUrl(updatedExpense.getReceiptUrl());
                expense.setNotes(updatedExpense.getNotes());
                expense.setMerchant(updatedExpense.getMerchant());
                return expenseRepo.save(expense);
            })
            .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id " + id));
}

@Transactional
public ExpenseTracker updateExpenseByItemDescription(String itemDescription, ExpenseTracker updatedExpense) {
    String email = getCurrentUserEmail();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    List<ExpenseTracker> expenses = expenseRepo.findByUserIdAndItemDescription(user.getId(), itemDescription);
    if (expenses.isEmpty()) {
        throw new ExpenseNotFoundException("Expense not found with item description " + itemDescription);
    }
    ExpenseTracker expense = expenses.get(0);
    expense.setItemDescription(updatedExpense.getItemDescription());
    expense.setDate(updatedExpense.getDate());
    expense.setAmount(updatedExpense.getAmount());
    expense.setCategory(updatedExpense.getCategory());
    expense.setSubCategory(updatedExpense.getSubCategory());
    expense.setPaymentMethod(updatedExpense.getPaymentMethod());
    expense.setReceiptUrl(updatedExpense.getReceiptUrl());
    expense.setNotes(updatedExpense.getNotes());
    expense.setMerchant(updatedExpense.getMerchant());
    return expenseRepo.save(expense);
}

    private String getCurrentUserEmail() {
           return (String) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
    }

   }


