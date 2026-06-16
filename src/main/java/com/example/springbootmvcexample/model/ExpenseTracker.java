package com.example.springbootmvcexample.model;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "expense_tracker")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExpenseTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    //item description is required
    @NotBlank(message = "Item description is required")
    private String itemDescription;

    // date is required
    @NotNull(message = "Date is required")
    private LocalDate date;

    // amount must be a positive number - this prevents negative and zero amounts
    @Positive(message = "Amount must be greater than zero")
    private double amount;

    private String category;
    private String subCategory;
    private String paymentMethod;
    private String receiptUrl;
    private String notes;
    private String merchant;
    
    
    

    
}
