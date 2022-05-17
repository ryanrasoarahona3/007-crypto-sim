package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;

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
                "    exchange_url varchar(255)\n" +
                ");\n";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void destroySQLTable() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS \"exchange\";\n");
    }

    @Override
    public void add(Object b) throws SQLException {

    }

    @Override
    public ArrayList getAll() throws SQLException {
        return null;
    }
}
