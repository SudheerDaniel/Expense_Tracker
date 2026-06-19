package com.example.springbootmvcexample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // new paginated version for the main expense list endpoint
    Page<ExpenseTracker> findByUserId(Long userId, Pageable pageable);

    // fetch a paginated filtered list of expenses for a user
    // category, paymentMethod and notes are optional - pass null to skip that filter
    @Query("SELECT e FROM ExpenseTracker e WHERE e.userId = :userId " + 
           "AND e.date BETWEEN :startDate AND :endDate " +
           "AND (:category IS NULL OR e.category = :category) " +
           "AND (:paymentMethod IS NULL OR e.paymentMethod = :paymentMethod) " +
           "AND (:notes IS NULL OR LOWER(CAST(e.notes AS string)) LIKE LOWER(CAST(CONCAT('%', :notes, '%')AS string)))")
    Page<ExpenseTracker> findFilteredExpenses(
                     @Param("userId") Long userId,
                     @Param("startDate") LocalDate startDate,
                     @Param("endDate") LocalDate endDate,
                     @Param("category") String category,
                     @Param("paymentMethod") String paymentMethod,
                     @Param("notes") String notes,
                     Pageable pageable);
    

    
}
