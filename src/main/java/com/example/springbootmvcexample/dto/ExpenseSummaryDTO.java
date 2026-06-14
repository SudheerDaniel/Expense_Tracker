package com.example.springbootmvcexample.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSummaryDTO {

    // total amount spent in the date range
    private double totalSpent;

    // breakdown of spending by category e.g. {"Food": 200.0, "Transport": 50.0}
    private Map<String, Double> byCategory;

    // breakdown of spending by payment method e.g. {"Credit card": 150.0, "Cash": 100,0}
    private Map<String, Double> byPaymentMethod;
}
