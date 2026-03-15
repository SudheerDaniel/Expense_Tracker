package com.example.springbootmvcexample.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "expense_tracker")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
public class ExpenseTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;
    private String itemDescription;
    private double date;
    private double amount;
    private String category;
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public String getItemDescription() {
        return itemDescription;
    }
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public double getDate() {
        return date;
    }
    public void setDate(double date) {
        this.date = date;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public ExpenseTracker() {
    }
    public ExpenseTracker(int number, String itemDescription, double date, double amount, String category) {
        this.number = number;
        this.itemDescription = itemDescription;
        this.date = date;
        this.amount = amount;
        this.category = category;
    }
    
    
    

    
}
