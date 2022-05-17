package com.crypto.cryptosim.services;

import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.ValuableCrypto;
import com.crypto.cryptosim.models.Transaction;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.structures.ChartData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public int totalCryptoValues(User u) throws SQLException {
        ArrayList<ValuableCrypto> cryptos = MarketManager.getInstance().getAll();
        int output = 0;
        for(int i = 0; i < cryptos.size(); i++){
            ValuableCrypto c = cryptos.get(i);
            int possessed = numberOfCoins(u, c);
            output+= possessed * c.getValue();
        }
        return output;
    }

    /**
     * VERY IMPORTANT
     * Il est primordial qu'il y a plus de 7 entrées dans la base de donnée, sinon, il y aura une erreur
     * @param c
     * @return
     */
    public float getWeeklyTrend(ValuableCrypto c) throws SQLException {
        String sql = "SELECT price_value FROM price WHERE price_crypto=? ORDER BY price_date DESC LIMIT 7;";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, c.getId());
        ResultSet rs = stmt.executeQuery();
        ArrayList<Integer> priceList = new ArrayList<>();
        while(rs.next()){
            priceList.add(rs.getInt("price_value"));
        }
        return (float)(priceList.get(6) - priceList.get(0)) / priceList.get(0);
    }

    public float getWeeklyTrend(User u) throws SQLException {
        float sumOfTrend = 0;
        int sumOfPossessed = 0;
        ArrayList<ValuableCrypto> cryptos = MarketManager.getInstance().getAll();
        for(int i = 0; i < cryptos.size(); i++){
            ValuableCrypto c = cryptos.get(i);
            float wTrend = getWeeklyTrend(c);
            int possessed = numberOfCoins(u, c);
            sumOfTrend+= wTrend*possessed;
            sumOfPossessed+= possessed;
        }
        return sumOfTrend/sumOfPossessed;
    }

    public ChartData getWalletChartData(User u) throws SQLException {
        ChartData output = new ChartData();
        output.labels = new ArrayList<>();
        output.data = new ArrayList<>();
        ArrayList<ValuableCrypto> cryptos = MarketManager.getInstance().getAll();
        for(int i = 0; i < cryptos.size(); i++) {
            ValuableCrypto c = cryptos.get(i);
            int possessed = numberOfCoins(u, c);
            if(possessed > 0) {
                output.labels.add(c.getSlug());
                output.data.add(c.getValue() * possessed);
            }
        }
        return output;
    }

    public ArrayList<ArrayList<String>> getTransactionDetails(User u) throws SQLException {
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        String sql = "SELECT * FROM transaction\n" +
                "LEFT JOIN crypto ON crypto_id=transaction_crypto\n" +
                "WHERE transaction_transmitter=? OR transaction_recipient=?\n" +
                "ORDER BY transaction_date DESC;";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, u.getId());
        stmt.setInt(2, u.getId());
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            int sum = rs.getInt("transaction_sum");
            String cryptoSlug = rs.getString("crypto_slug");
            int cryptoQty = rs.getInt("transaction_crypto_n");
            ArrayList<String> row = new ArrayList<>();
            row.add(rs.getDate("transaction_date").toString());
            row.add("#TRA_" + rs.getString("transaction_id"));

            if(rs.getObject("transaction_crypto") == null) {
                if (rs.getObject("transaction_transmitter") == null)
                    row.add("Depot de " + sum + " €");
                else
                    row.add("Retrait de " + sum + " €");
            }else{
                if (rs.getObject("transaction_transmitter") == null)
                    row.add("Vente de " + cryptoQty + " " + cryptoSlug + " pour " + sum + " €" );
                else
                    row.add("Achat de " + cryptoQty + " " + cryptoSlug + " pour " + sum + " €" );
            }

            if (rs.getObject("transaction_transmitter") == null){
                row.add("");
                row.add("" + sum + " €");
            }else{
                row.add("" + sum + " €");
                row.add("");
            }

            output.add(row);
        }
        return output;
    }
}
