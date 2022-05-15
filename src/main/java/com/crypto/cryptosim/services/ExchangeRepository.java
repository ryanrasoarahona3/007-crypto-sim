package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractRepository;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExchangeRepository extends AbstractRepository {

    private static ExchangeRepository instance = null;
    public static ExchangeRepository getInstance(){
        if(instance == null)
            instance = new ExchangeRepository();
        return instance;
    }
    public static void tearDown(){
        ExchangeRepository.instance = null;
    }

    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS \"exchange\";\n" +
                "CREATE TABLE \"exchange\" (\n" +
                "    exchange_id serial PRIMARY KEY,\n" +
                "    logo text,\n" +
                "    name varchar(255),\n" +
                "    url varchar(255)\n" +
                ");";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void add(Object b) throws SQLException {

    }

    @Override
    public ArrayList getAll() throws SQLException {
        return null;
    }
}
