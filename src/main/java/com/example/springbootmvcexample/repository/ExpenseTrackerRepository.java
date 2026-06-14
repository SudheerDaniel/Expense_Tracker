package com.example.springbootmvcexample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import com.example.springbootmvcexample.model.ExpenseTracker;

@Repository
public interface ExpenseTrackerRepository extends JpaRepository<ExpenseTracker, Long>  {

    List<ExpenseTracker> findByCategory(String category);

    

    @Transactional
    void deleteByCategory(String category);



    List<ExpenseTracker> findByItemDescription(String itemDescription);

    List<ExpenseTracker> findBySubCategory(String subCategory);

    List<ExpenseTracker> findByMerchant(String merchant);

    List<ExpenseTracker> findByPaymentMethod(String paymentMethod);

    List<ExpenseTracker> findByNotesContainingIgnoreCase(String notes);

    List<ExpenseTracker> findByUserIdAndCategory (Long userId, String category);

    List<ExpenseTracker> findByUserIdAndItemDescription(Long userId, String itemDescription);

    List<ExpenseTracker> findByUserIdAndSubCategory(Long userId, String subCategory);

    List<ExpenseTracker> findByUserIdAndMerchant(Long userId, String merchant);

    List<ExpenseTracker> findByUserIdAndPaymentMethod(Long userId, String paymentMethod);

    List<ExpenseTracker> findByUserIdAndNotesContainingIgnoreCase(Long userId, String notes);

    List<ExpenseTracker> findByUserId(Long userId);

    //fetch all expenses for a user within a date range
    List<ExpenseTracker> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    //fetch all expenses for a user within a date range filtered by category
    List<ExpenseTracker> findByUserIdAndDateBetweenAndCategory(Long userId, LocalDate startDate, LocalDate endDate, String category);

    //fetch all expenses for a user within a date ranfe filtered by payment
    List<ExpenseTracker> findByUserIdAndDateBetweenAndPaymentMethod(Long userId, LocalDate startDate, LocalDate endDate, String paymentMethod);

    @Transactional
    void deleteByUserIdAndId(Long userId, Long id);

    @Transactional
    void deleteByUserIdAndCategory(Long userId, String category);

    

    
}
