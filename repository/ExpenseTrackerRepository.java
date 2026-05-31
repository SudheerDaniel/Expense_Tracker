package com.example.springbootmvcexample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootmvcexample.model.ExpenseTracker;

@Repository
public interface ExpenseTrackerRepository extends JpaRepository<ExpenseTracker, Integer>  {

    List<ExpenseTracker> findByCategory(String category);

    

    @Transactional
    void deleteByCategory(String category);



    List<ExpenseTracker> findByItemDescription(String itemDescription);

    List<ExpenseTracker> findBySubCategory(String subCategory);

    List<ExpenseTracker> findByMerchant(String merchant);

    List<ExpenseTracker> findByPaymentMethod(String paymentMethod);

    List<ExpenseTracker> findByNotesContainingIgnoreCase(String notes);

    

    
}
