package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;
import com.crypto.cryptosim.models.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TransactionDAO extends AbstractDAO {

    private static TransactionDAO instance = null;
    public static TransactionDAO getInstance(){
        if(instance == null)
            instance = new TransactionDAO();
        return instance;
    }
    public static void tearDown(){
        TransactionDAO.instance = null;
    }


    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "" +
                "CREATE TABLE \"transaction\" (\n" +
                "    transaction_id serial PRIMARY KEY,\n" +
                "    transaction_transmitter INT NULL,\n" +
                "    transaction_recipient INT NULL,\n" +
                "    transaction_crypto INT NULL,\n" +
                "    transaction_crypto_n INT NULL,\n" +
                "    transaction_sum INT,\n" +
                "    transaction_exchange INT NULL,\n" +
                "    CONSTRAINT fk_transmitter FOREIGN KEY (transaction_transmitter) REFERENCES \"user\"(user_id),\n" +
                "    CONSTRAINT fk_recipient FOREIGN KEY (transaction_recipient) REFERENCES \"user\"(user_id),\n" +
                "    CONSTRAINT fk_crypto FOREIGN KEY (transaction_crypto) REFERENCES crypto(crypto_id),\n" +
                "    CONSTRAINT fk_exchange FOREIGN KEY (transaction_exchange) REFERENCES exchange(exchange_id)\n" +
                ");";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void destroySQLTable() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS \"transaction\";\n");
    }

    private Transaction getFromResultSet(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setId(rs.getInt("transaction_id"));
        t.setTransmitterId(rs.getInt("transaction_transmitter"));
        t.setRecipientId(rs.getInt("transaction_recipient"));
        t.setCryptoId(rs.getInt("transaction_crypto"));
        t.setCryptoN(rs.getInt("transaction_crypto_n"));
        t.setSum(rs.getInt("transaction_sum"));
        t.setExchangeId(rs.getInt("transaction_exchange"));
        return t;
    }

    @Override
    public void add(Object b) throws SQLException {
        Transaction t = (Transaction) b;
        String sql = "INSERT INTO \"transaction\" (transaction_transmitter, transaction_recipient, transaction_crypto, " +
                "transaction_crypto_n, transaction_sum, transaction_exchange) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        if(t.getTransmitterId() != 0) stmt.setInt(1, t.getTransmitterId());
        else stmt.setObject(1, null);

        if(t.getRecipientId() != 0) stmt.setInt(2, t.getRecipientId());
        else stmt.setObject(2, null);

        if(t.getCryptoId() != 0) stmt.setInt(3, t.getCryptoId());
        else stmt.setObject(3, null);

        if(t.getCryptoN() != 0) stmt.setInt(4, t.getCryptoN());
        else stmt.setObject(4, null);

        stmt.setInt(5, t.getSum());

        if(t.getExchangeId() != 0) stmt.setInt(6, t.getExchangeId());
        stmt.setObject(6, null);

        stmt.execute();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        t.setId(auto_id);
    }

    @Override
    public ArrayList getAll() throws SQLException {
        PreparedStatement stmt = null;
        ArrayList<Transaction> output = new ArrayList<>();
        stmt = getConnection().prepareStatement("SELECT * from \"transaction\"");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add(getFromResultSet(rs));
        }
        return output;
    }
}
