package com.crypto.cryptosim;

import com.crypto.cryptosim.structures.ChartData;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PriceCurve{
    private static PriceCurve instance = null;
    public static PriceCurve getInstance(){
        if(instance == null){
            instance = new PriceCurve();
        }
        return instance;
    }

    /**
     * Get Database connection
     * @return
     */
    protected Connection getConnection() throws SQLException {
        return DatabaseManager.getInstance().getConnection();
    }

    public ArrayList<Integer> getLastMonthVariation(ValuableCrypto c) throws SQLException {
        ArrayList<Integer> output = new ArrayList<>();
        PreparedStatement stmt = getConnection().prepareStatement("SELECT price_value FROM price WHERE price_crypto=? ORDER BY price_date DESC LIMIT 30;");
        stmt.setInt(1, c.getId());
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
            output.add(rs.getInt("price_value"));
        return output;
    }

    public String getLastMonthVariationJson(ValuableCrypto c) throws SQLException{
        ArrayList<Integer> variation = getLastMonthVariation(c);
        String json = new Gson().toJson(variation);
        return json;
    }

    public ChartData getLastNDaysVariation(ValuableCrypto c, int n) throws SQLException {
        ChartData output = new ChartData();
        ArrayList<Integer> prices = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<String> colors = new ArrayList<>();

        PreparedStatement stmt = getConnection().prepareStatement("SELECT price_date, price_value FROM price WHERE price_crypto=? ORDER BY price_date DESC LIMIT ?;");
        stmt.setInt(1, c.getId());
        stmt.setInt(2, n);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            // TODO: a mettre en français
            labels.add(rs.getDate("price_date").toString());
            prices.add(rs.getInt("price_value"));


            int s = prices.size();
            if(s == 1) // last element
                 colors.add("orange");
            else {
                colors.add((prices.get(s-1)>prices.get(s-2))?"#00ff00":"#ff0000");
            }
        }
        output.data = Utils.reverseArray(prices);
        output.labels = Utils.reverseArray(labels);
        output.colors = Utils.reverseArray(colors);
        return output;
    }
}
