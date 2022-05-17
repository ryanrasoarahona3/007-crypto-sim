package com.crypto.cryptosim.services;

import com.crypto.cryptosim.ValuableCrypto;
import com.crypto.cryptosim.models.Transaction;
import com.crypto.cryptosim.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Cette classe a été créée pour gérer les transactions courantes
 */
public class TransactionManager extends TransactionDAO {

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

    public void buyCoin(User u, ValuableCrypto c, int n) throws SQLException {
        Transaction t = new Transaction();
        t.setTransmitterId(u.getId());
        t.setSum(c.getValue() * n);
        t.setCryptoId(c.getId());
        t.setCryptoN((n));
        add(t);
    }

    public void sellCoin(User u, ValuableCrypto c, int n) throws SQLException {
        Transaction t = new Transaction();
        t.setRecipientId(u.getId());
        t.setSum(c.getValue() * n);
        t.setCryptoId(c.getId());
        t.setCryptoN((n));
        add(t);
    }

    public int totalPurchased(User u, ValuableCrypto c) throws SQLException {
        String sql = "SELECT sum(transaction_crypto_n) as total_purchased FROM transaction WHERE transaction_transmitter=? AND transaction_crypto=?";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, u.getId());
        stmt.setInt(2, c.getId());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("total_purchased");
    }

    // Total des cryptos vendus (pour comptabiliser à la fin)
    public int totalSold(User u, ValuableCrypto c) throws SQLException {
        String sql = "SELECT sum(transaction_crypto_n) as total_sold FROM transaction WHERE transaction_recipient=? AND transaction_crypto=?";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, u.getId());
        stmt.setInt(2, c.getId());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("total_sold");
    }

    public int numberOfCoins(User u, ValuableCrypto c) throws SQLException {
        return totalPurchased(u, c) - totalSold(u, c);
    }
}
