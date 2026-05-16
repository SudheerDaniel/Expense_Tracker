package com.example.springbootmvcexample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springbootmvcexample.model.ExpenseTracker;
import com.example.springbootmvcexample.repository.ExpenseTrackerRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpenseTrackerService {
    /*List <expenseTracker> expenses = new ArrayList<>();
    expenseTrackerRepository exr = new expenseTrackerRepository();
    public List<expenseTracker> getAllExpenses(){
        return exr.getAll();
    }
    public void addExpenses(expenseTracker expense){
       // expenses.add(expense);
       exr.save(expense);
    }
    public List<expenseTracker> getExpenseByCategory(String category){
        return exr.getExpenseByCat(category);
    }
        
    public void deleteExpense(String itemDescription){
        exr.delete(itemDescription);
    }
    public void updateExpense(String oldItemDescription, String itemDescription, double date, double amount,String category){

        exr.update(oldItemDescription, itemDescription, date, amount, category);
    }
    public List<expenseTracker> updateExpenseByItemDescription(String itemDescription){
        for(expenseTracker expense: expenses){
            List<expenseTracker> y = new ArrayList<>();
            if(expense.getItemDescription().equals(itemDescription)){
                y.add(expense);
                return y;
            }

        }
        return null;
    }*/
   @Autowired
   private ExpenseTrackerRepository expenseRepo;
   public List<ExpenseTracker> getAllExpenses() {
     return expenseRepo.findAll();
   }
   public List<ExpenseTracker> getExpenseByCategory(String category) {
         return expenseRepo.findByCategory(category);
   }
   public void addExpenses(ExpenseTracker expense){
        expenseRepo.save(expense);
   }
   public List<ExpenseTracker> getExpenseByItemDescription (String itemDescription){
        return expenseRepo.findByItemDescription(itemDescription);
   }
   public void deleteExpense(int id){
        expenseRepo.deleteById(id);
   }
   @Transactional
   public void deleteExpenseByCategory(String category){
        expenseRepo.deleteByCategory(category);
   }
   @Transactional
   public ExpenseTracker updateExpense(int id, ExpenseTracker updatedExpense){
        return expenseRepo.findById(id)
        .map(expense -> {
            expense.setItemDescription(updatedExpense.getItemDescription());
            expense.setDate(updatedExpense.getDate());
            expense.setAmount(updatedExpense.getAmount());
            expense.setCategory(updatedExpense.getCategory());
            return expenseRepo.save(expense);
        })
        .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));

   }
   @Transactional
   public ExpenseTracker updateExpenseByItemDescription(String itemDescription, ExpenseTracker updatedExpense){
        List<ExpenseTracker> expenses = expenseRepo.findByItemDescription(itemDescription);
        if (expenses.isEmpty()) {
            throw new RuntimeException("Expense not found with item description " + itemDescription);
        }
        ExpenseTracker expense = expenses.get(0); // Assuming itemDescription is unique
        expense.setItemDescription(updatedExpense.getItemDescription());
        expense.setDate(updatedExpense.getDate());
        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        return expenseRepo.save(expense);

   }
}

