package com.example.springbootmvcexample.model;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "expense_tracker")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String itemDescription;
    private LocalDate date;
    private double amount;
    private String category;
    private String subCategory;
    private String paymentMethod;
    private String receiptUrl;
    private String notes;
    private String merchant;
    
    
    

    
}
