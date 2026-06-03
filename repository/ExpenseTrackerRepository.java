package com.example.springbootmvcexample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    void deleteByUserIdAndId(Long userId, Long id);

    @Transactional
    void deleteByUserIdAndCategory(Long userId, String category);

    

    
}
