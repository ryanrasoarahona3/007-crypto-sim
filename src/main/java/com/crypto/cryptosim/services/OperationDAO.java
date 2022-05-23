package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OperationDAO extends AbstractDAO {
    private static OperationDAO instance = null;
    public static OperationDAO getInstance(){
        if(instance == null)
            instance = new OperationDAO();
        return instance;
    }
    public static void tearDown(){
        OperationDAO.instance = null;
    }

    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "CREATE TABLE \"operation\" (\n" +
                "    operation_id serial PRIMARY KEY,\n" +
                "    operation_origin int null, -- Contrary to \"transaction\", this is a wallet, not a user\n" +
                "    operation_destination int null, -- Same same\n" +
                "    operation_crypto int null,\n" +
                "    operation_crypto_n int null,\n" +
                "    operation_sum int,\n" +
                "    operation_exchange int null,\n" +
                "    operation_date date not null,\n" +
                "    CONSTRAINT fk_origin FOREIGN KEY (operation_origin) REFERENCES \"wallet\"(wallet_id),\n" +
                "    CONSTRAINT fk_destination FOREIGN KEY (operation_destination) REFERENCES \"wallet\"(wallet_id),\n" +
                "    CONSTRAINT fk_crypto FOREIGN KEY (operation_crypto) REFERENCES crypto(crypto_id),\n" +
                "    CONSTRAINT fk_exchange FOREIGN KEY (operation_exchange) REFERENCES exchange(exchange_id)\n" +
                ")";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void destroySQLTable() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS \"operation\";\n");
    }

    @Override
    public void add(Object b) throws SQLException {

    }

    @Override
    public ArrayList getAll() throws SQLException {
        return null;
    }
}
