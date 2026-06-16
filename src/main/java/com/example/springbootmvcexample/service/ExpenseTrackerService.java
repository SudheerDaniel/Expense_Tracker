package com.example.springbootmvcexample.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;
import com.example.springbootmvcexample.exception.ExpenseNotFoundException;
import com.example.springbootmvcexample.model.ExpenseTracker;
import com.example.springbootmvcexample.model.User;
import com.example.springbootmvcexample.repository.ExpenseTrackerRepository;
import com.example.springbootmvcexample.repository.UserRepository;
import com.example.springbootmvcexample.dto.ExpenseSummaryDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseTrackerService {
   private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExpenseTrackerService.class);
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
   

        public boolean isReceiptOwnedByUser(String email, String key) {
          User user = userRepository.findByEmail(email)
                  .orElseThrow(() -> new RuntimeException("User not found"));
          List<ExpenseTracker> expenses = expenseRepo.findByUserId(user.getId());
          log.debug("Checking receipt ownership for email: {}, key:{}", email, key);
          log.debug("User has {} expenses", expenses.size());
          expenses.forEach(e -> log.debug("Receipt URL: {}", e.getReceiptUrl()));
          return expenseRepo.findByUserId(user.getId())
                  .stream()
                  .anyMatch(e -> e.getReceiptUrl() != null && e.getReceiptUrl().contains(key));
      }

      // returns total spent, breakdown by category, and breakdown by payment method
      // for the given user within the date range
      public ExpenseSummaryDTO getExpenseSummary(LocalDate startDate, LocalDate endDate, String category, String paymentMethod) {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
               .orElseThrow(() -> new RuntimeException("User not found"));
        List<ExpenseTracker> expenses = expenseRepo.findByUserIdAndDateBetween(user.getId(), startDate, endDate);
             
        //apply optional category filter
        if (category != null && !category.isBlank()) {
            expenses = expenses.stream()
                   .filter(e -> category.equals(e.getCategory()))
                   .collect(Collectors.toList());
        }
  
        //apply optional payment method filter
        if (paymentMethod != null && !paymentMethod.isBlank()) {
            expenses = expenses.stream()
                   .filter(e -> paymentMethod.equals(e.getPaymentMethod()))
                   .collect(Collectors.toList());
        }

        double totalSpent = expenses.stream()
                .mapToDouble(ExpenseTracker::getAmount)
                .sum();
        Map<String, Double> byCategory = expenses.stream()
                .filter(e -> e.getCategory() != null)
                .collect(Collectors.groupingBy(
                        ExpenseTracker::getCategory,
                        Collectors.summingDouble(ExpenseTracker::getAmount)
                ));
        Map<String, Double> byPaymentMethod = expenses.stream()
               .filter(e -> e.getPaymentMethod() != null)
               .collect(Collectors.groupingBy(
                        ExpenseTracker::getPaymentMethod,
                        Collectors.summingDouble(ExpenseTracker::getAmount)
                ));
        return new ExpenseSummaryDTO(totalSpent, byCategory, byPaymentMethod);
        }

   }


