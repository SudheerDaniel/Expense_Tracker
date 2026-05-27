package com.example.springbootmvcexample.model;


import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


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
    // public Integer getId() {
    //     return id;
    // }
    // public void setId(Integer id) {
    //     this.id = id;
    // }
    // public String getItemDescription() {
    //     return itemDescription;
    // }
    // public void setItemDescription(String itemDescription) {
    //     this.itemDescription = itemDescription;
    // }
    // public LocalDate getDate() {
    //     return date;
    // }
    // public void setDate(LocalDate date) {
    //     this.date = date;
    // }
    // public double getAmount() {
    //     return amount;
    // }
    // public void setAmount(double amount) {
    //     this.amount = amount;
    // }
    // public String getCategory() {
    //     return category;
    // }
    // public void setCategory(String category) {
    //     this.category = category;
    // }
    // public String getSubCategory() {
    //     return subCategory;
    // }
    // public void setSubCategory(String subCategory) {
    //     this.subCategory = subCategory;
    // }
    // public String getPaymentMethod() {
    //     return paymentMethod;
    // }
    // public void setPaymentMethod(String paymentMethod) {
    //     this.paymentMethod = paymentMethod;
    // }
    // public String getReceiptUrl() {
    //     return receiptUrl;
    // }
    // public void setReceiptUrl(String receiptUrl) {
    //     this.receiptUrl = receiptUrl;
    // }
    // public String getNotes() {
    //     return notes;
    // }
    // public void setNotes(String notes) {
    //     this.notes = notes;
    // }
    // public String getMerchant() {
    //     return merchant;
    // }
    // public void setMerchant(String merchant) {
    //     this.merchant = merchant;
    // }
    // public ExpenseTracker() {
    // }
    // public ExpenseTracker(int id, String itemDescription, LocalDate date, double amount, String category, String subCategory, String paymentMethod, String receiptUrl, String notes, String merchant) {
    //     this.id = id;
    //     this.itemDescription = itemDescription;
    //     this.date = date;
    //     this.amount = amount;
    //     this.category = category;
    //     this.subCategory = subCategory;
    //     this.paymentMethod = paymentMethod;
    //     this.receiptUrl = receiptUrl;
    //     this.notes = notes;
    //     this.merchant = merchant;
    // }
    
    
    

    
}
