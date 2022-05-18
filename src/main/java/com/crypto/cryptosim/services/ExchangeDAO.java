package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;
import com.crypto.cryptosim.models.Exchange;
import com.crypto.cryptosim.models.Message;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExchangeDAO extends AbstractDAO {

    private static ExchangeDAO instance = null;
    public static ExchangeDAO getInstance(){
        if(instance == null)
            instance = new ExchangeDAO();
        return instance;
    }
    public static void tearDown(){
        ExchangeDAO.instance = null;
    }

    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "" +
                "CREATE TABLE \"exchange\" (\n" +
                "    exchange_id serial PRIMARY KEY,\n" +
                "    exchange_logo text,\n" +
                "    exchange_name varchar(255),\n" +
                "    exchange_url varchar(255),\n" +
                "    exchange_vol_day int,\n" +
                "    exchange_vol_week int,\n" +
                "    exchange_vol_month int,\n" +
                "    exchange_vol_liquidity int\n" +
                ");\n";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void destroySQLTable() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS \"exchange\";\n");
    }

    private Exchange getFromResultSet(ResultSet rs) throws SQLException {
        Exchange e = new Exchange();
        e.setId(rs.getInt("exchange_id"));
        e.setLogo(rs.getString("exchange_logo"));
        e.setName(rs.getString("exchange_name"));
        e.setUrl(rs.getString("exchange_url"));
        e.setVolDay(rs.getInt("exchange_vol_day"));
        e.setVolWeek(rs.getInt("exchange_vol_week"));
        e.setVolMonth(rs.getInt("exchange_vol_month"));
        e.setVolLiquidity(rs.getInt("exchange_vol_liquidity"));
        return e;
    }

    @Override
    public void add(Object b) throws SQLException {
        Exchange e = (Exchange)b;
        PreparedStatement stmt = getConnection().prepareStatement(
                "INSERT INTO exchange (exchange_logo, exchange_name, exchange_url, exchange_vol_day, exchange_vol_week," +
                        "exchange_vol_month, exchange_vol_liquidity) VALUES (?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, e.getLogo());
        stmt.setString(2, e.getName());
        stmt.setString(3, e.getUrl());
        stmt.setInt(4, e.getVolDay());
        stmt.setInt(5, e.getVolWeek());
        stmt.setInt(6, e.getVolMonth());
        stmt.setInt(7, e.getVolLiquidity());
        stmt.execute();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        e.setId(auto_id);
    }

    @Override
    public ArrayList getAll() throws SQLException {
        PreparedStatement stmt = null;
        ArrayList<Exchange> output = new ArrayList<>();
        stmt = getConnection().prepareStatement("SELECT * from \"exchange\"");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add(getFromResultSet(rs));
        }
        return output;
    }
}
