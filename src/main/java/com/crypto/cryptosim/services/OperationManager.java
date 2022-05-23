package com.crypto.cryptosim.services;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.ValuableCrypto;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.models.UserOperation;
import com.crypto.cryptosim.models.Wallet;
import com.crypto.cryptosim.models.WalletOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class has the role to manage every account movement (currency or crypto)
 */
public class OperationManager {

    private static OperationManager instance = null;
    public static OperationManager getInstance(){
        if(instance == null)
            instance = new OperationManager();
        return instance;
    }
    public static void tearDown(){
        OperationManager.instance = null;
    }

    protected Connection getConnection() throws SQLException {
        return DatabaseManager.getInstance().getConnection();
    }

    protected UserOperationDAO uod;
    protected WalletOperationDAO wod;
    protected MarketManager mm;

    // due to lack of skills, I have divided the code getBalance into 4 subparts
    private int totalUserIncome(User u) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT sum(user_operation_sum) as total_income FROM user_operation WHERE user_operation_destination=?;");
        stmt.setInt(1, u.getId());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("total_income");
    }

    private int totalUserOutcome(User u) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT sum(user_operation_sum) as total_outcome FROM user_operation WHERE user_operation_origin=?;");
        stmt.setInt(1, u.getId());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("total_outcome");
    }

    private int totalFromWalletIncome(User u) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT sum(wallet_operation_sum) as total_income FROM wallet_operation INNER JOIN wallet ON wallet_id=wallet_operation_origin WHERE wallet_user=?"
        );
        stmt.setInt(1, u.getId());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("total_income");
    }

    private int totalToWalletOutcome(User u) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT sum(wallet_operation_sum) as total_outcome FROM wallet_operation INNER JOIN wallet ON wallet_id=wallet_operation_destination WHERE wallet_user=?"
        );
        stmt.setInt(1, u.getId());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("total_outcome");
    }

    public int getBalance(User u) throws SQLException {
        return totalUserIncome(u) + totalFromWalletIncome(u) - totalUserOutcome(u) - totalToWalletOutcome(u);
    }

    public OperationManager() {
        uod = UserOperationDAO.getInstance();
        wod = WalletOperationDAO.getInstance();
        mm = MarketManager.getInstance();
    }

    public void deposit(User u, int sum) throws SQLException {
        UserOperation o = new UserOperation();
        o.setDestination(u.getId());
        o.setSum(sum);
        uod.add(o);
    }

    public void withdrawal(User u, int sum) throws SQLException {
        UserOperation o = new UserOperation();
        o.setOrigin(u.getId());
        o.setSum(sum);
        uod.add(o);
    }

    public void buyCrypto(Wallet w, int n) throws SQLException {
        int sum = n * mm.cryptoById(w.getCryptoId()).getValue();

        WalletOperation wo = new WalletOperation();
        wo.setDestination(w.getId());
        wo.setN(n);
        wo.setSum(sum);
        wod.add(wo);
    }

    public void sellCrypto(Wallet w, int n) throws SQLException {
        int sum = n * mm.cryptoById(w.getCryptoId()).getValue();

        WalletOperation wo = new WalletOperation();
        wo.setOrigin(w.getCryptoId());
        wo.setN(n);
        wo.setSum(sum);
        wod.add(wo);
    }

    private int totalPurchased(Wallet w) throws SQLException {
        String sql = "SELECT sum(wallet_operation_n) as total_purchased FROM wallet_operation WHERE wallet_operation_destination=?;";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, w.getId());ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("total_purchased");
    }

    private int totalSold(Wallet w) throws SQLException {
        String sql = "SELECT sum(wallet_operation_n) as total_sold FROM wallet_operation WHERE wallet_operation_origin=?;";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, w.getId());ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("total_sold");
    }

    public int numberOfCoins(Wallet w) throws SQLException {
        return totalPurchased(w) - totalSold(w);
    }
}
