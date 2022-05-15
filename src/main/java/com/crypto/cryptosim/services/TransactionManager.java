package com.crypto.cryptosim.services;

import com.crypto.cryptosim.models.Transaction;
import com.crypto.cryptosim.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Cette classe a été créée pour gérer les transactions courantes
 */
public class TransactionManager extends TransactionRepository{

    private static TransactionManager instance = null;
    public static TransactionManager getInstance(){
        if(instance == null)
            instance = new TransactionManager();
        return instance;
    }
    public static void tearDown(){
        TransactionManager.instance = null;
    }


    public void deposit(User u, int sum) throws SQLException {
        Transaction t = new Transaction();
        t.setRecipientId(u.getId());
        t.setSum(sum);
        add(t);
    }

    public void withdrawal(User u, int sum) throws SQLException {
        Transaction t = new Transaction();
        t.setTransmitterId(u.getId());
        t.setSum(sum);
        add(t);
    }

    private int totalIncome(User u) throws SQLException{
        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT sum(transaction_sum) as total_income FROM transaction WHERE transaction_recipient=?;");
        stmt.setInt(1, u.getId());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("total_income");
    }

    private int totalOutcome(User u) throws SQLException{
        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT sum(transaction_sum) as total_outcome FROM transaction WHERE transaction_transmitter=?;");
        stmt.setInt(1, u.getId());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("total_outcome");
    }

    public int getBalance(User u) throws SQLException {
        return totalIncome(u) - totalOutcome(u);
    }
}
