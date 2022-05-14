package com.crypto.cryptosim;

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
    protected Connection getConnection(){
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
}
