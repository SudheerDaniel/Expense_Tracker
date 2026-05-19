package com.example.springbootmvcexample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootmvcexample.model.ExpenseTracker;

/*public class expenseTrackerRepository {
    Connection cion = null;
    public expenseTrackerRepository() {
        try {
            cion = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/expenses",  "sudheerdanielmeghavaram",  "13456789Sudh@");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void save(expenseTracker expense) {
        String insertQyery = "INSERT INTO expense_tracker (item_description, date, amount, category) VALUES (?,?,?,?)";
        try {
            PreparedStatement it = cion.prepareStatement(insertQyery);
            it.setString(1, expense.getItemDescription());
            it.setDouble(2,expense.getDate());
            it.setDouble(3, expense.getAmount());
            it.setString(4, expense.getCategory());
            it.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(String oldItemDescription, String itemDescription, double date, double amount, String category) {
        String updateQuery = " UPDATE expense_tracker SET item_description = ?, date = ?, amount = ?, category = ? WHERE item_description = ?";
        try {
            PreparedStatement ut = cion.prepareStatement (updateQuery);
            ut.setString(1, itemDescription);
            ut.setDouble(2, date);
            ut.setDouble(3, amount);
            ut.setString(4, category);
            ut.setString(5, oldItemDescription);
            ut.executeUpdate();
        } catch (Exception e) {
        }
    }
    public List<expenseTracker> getAll() {
        List<expenseTracker> expenses = new ArrayList<>();
        String selectQuery = " SELECT item_description, date, amount, category FROM expense_tracker ";
        try {
            PreparedStatement st = cion.prepareStatement(selectQuery);
            ResultSet vv = st.executeQuery();
            while (vv.next()) {
                expenseTracker expense = new expenseTracker();
                expense.setItemDescription(vv.getString(1));
                expense.setDate(vv.getDouble(2));
                expense.setAmount(vv.getDouble(3));
                expense.setCategory(vv.getString(4));
                expenses.add(expense);

            }

        } catch (Exception e) {
        }
        return expenses;
    }
    public void delete(String itemDescription) {
        String deleteQuery = " DELETE FROM expense_tracker WHERE item_description = ? ";
        try {
            PreparedStatement cr = cion.prepareStatement(deleteQuery);
            cr.setString(1, itemDescription);
            cr.executeQuery();
        } catch (Exception e) {
        }
    }

    public List<expenseTracker> getExpenseByCat(String category) {
        String selectQuery1 = " SELECT item_description, date, amount, category FROM expense_tracker WHERE category = ? ";
        List<expenseTracker> expenses = new ArrayList<>();
        try {
            PreparedStatement st = cion.prepareStatement(selectQuery1);
            st.setString(1, category);
            ResultSet ufy = st.executeQuery();
            while(ufy.next()) {
                expenseTracker expense = new expenseTracker();
                expense.setItemDescription(ufy.getString(1));
                expense.setDate(ufy.getDouble(2));
                expense.setAmount(ufy.getDouble(3));
                expense.setCategory(ufy.getString(4));
                expenses.add(expense);
            }
        } catch (Exception e) {
        }
        return expenses;
    }
}*/
@Repository
public interface ExpenseTrackerRepository extends JpaRepository<ExpenseTracker, Integer>  {

    List<ExpenseTracker> findByCategory(String category);

    

    @Transactional
    void deleteByCategory(String category);



    List<ExpenseTracker> findByItemDescription(String itemDescription);

    List<ExpenseTracker> findBySubCategory(String subCategory);

    List<ExpenseTracker> findByMerchant(String merchant);

    List<ExpenseTracker> findByPaymentMethod(String paymentMethod);

    List<ExpenseTracker> findByNotes(String notes);

    

    
}
