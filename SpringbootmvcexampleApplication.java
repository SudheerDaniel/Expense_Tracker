package com.example.springbootmvcexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringbootmvcexampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootmvcexampleApplication.class, args);

		//JDBC code
		/*expenseTrackerService exs = new expenseTrackerService();
		expenseTracker ex1 = new expenseTracker(1, "Lunch", 11032025, 15.50, "Food");
		exs.addExpenses(ex1);
		expenseTracker ex2 = new expenseTracker(2, "Bus Ticket", 11032025, 2.75, "Transport");
		exs.addExpenses(ex2);
		List<expenseTracker> allExpenses = exs.getAllExpenses();
		for(expenseTracker expense: allExpenses){
			System.out.println(expense);
		}
		exs.deleteExpense("Lunch");

		exs.updateExpenseByItemDescription("Bus Ticket");
		List<expenseTracker> allExpenses =exs.getExpenseByCategory("Transport");
		for(expenseTracker expense: allExpenses){
			System.out.println(expense);
		}
		expenseTrackerService f = new expenseTrackerService();
		f.updateExpense("Bus Ticket", "old Navy", 11282025, 49.97, "Clothes");*/
		
		//JPA code
		/*ApplicationContext context = SpringApplication.run( SpringbootmvcexampleApplication.class, args);
		ExpenseTrackerService expenseService = context.getBean(ExpenseTrackerService.class);
		expenseService.getAllExpenses().forEach(System.out::println);*/
	




	}

}
