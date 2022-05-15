package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractRepository;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TransactionRepository extends AbstractRepository {

    private static TransactionRepository instance = null;
    public static TransactionRepository getInstance(){
        if(instance == null)
            instance = new TransactionRepository();
        return instance;
    }
    public static void tearDown(){
        TransactionRepository.instance = null;
    }


    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS \"transaction\";\n" +
                "CREATE TABLE \"transaction\" (\n" +
                "    transaction_id serial PRIMARY KEY,\n" +
                "    transaction_transmitter INT,\n" +
                "    transaction_recipient INT,\n" +
                "    transaction_crypto INT,\n" +
                "    transaction_sum INT,\n" +
                "    transaction_exchange INT,\n" +
                "    CONSTRAINT fk_transmitter FOREIGN KEY (transaction_transmitter) REFERENCES user(user_id),\n" +
                "    CONSTRAINT fk_recipient FOREIGN KEY (transaction_recipient) REFERENCES user(user_id),\n" +
                "    CONSTRAINT fk_crypto FOREIGN KEY (transaction_crypto) REFERENCES crypto(crypto_id),\n" +
                "    CONSTRAINT fk_exchange FOREIGN KEY (transaction_exchange) REFERENCES exchange(transaction_id)\n" +
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
