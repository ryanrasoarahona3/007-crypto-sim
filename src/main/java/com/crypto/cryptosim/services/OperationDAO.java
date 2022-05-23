package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.models.Operation;
import com.crypto.cryptosim.models.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private Operation getFromResultSet(ResultSet rs) throws SQLException {
        Operation o = new Operation();
        o.setId(rs.getInt("operation_id"));
        o.setOrigin(rs.getInt("operation_origin"));
        o.setDestination(rs.getInt("operation_destination"));
        o.setCryptoId(rs.getInt("operation_crypto"));
        o.setCryptoN(rs.getInt("operation_crypto_n"));
        o.setSum(rs.getInt("operation_sum"));
        o.setExchangeId(rs.getInt("operation_exchange"));
        //o.setDate(rs.getDate("operation_date").toLocalDate());
        return o;
    }

    @Override
    public void add(Object b) throws SQLException {
        Operation t = (Operation) b;
        String sql = "INSERT INTO \"operation\" (operation_origin, operation_destination, operation_crypto, " +
                "operation_crypto_n, operation_sum, operation_exchange, operation_date) VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        if(t.getOrigin() != 0) stmt.setInt(1, t.getOrigin());
        else stmt.setObject(1, null);

        if(t.getDestination() != 0) stmt.setInt(2, t.getDestination());
        else stmt.setObject(2, null);

        if(t.getCryptoId() != 0) stmt.setInt(3, t.getCryptoId());
        else stmt.setObject(3, null);

        if(t.getCryptoN() != 0) stmt.setInt(4, t.getCryptoN());
        else stmt.setObject(4, null);

        stmt.setInt(5, t.getSum()); // Une valeur obligatoire

        if(t.getExchangeId() != 0) stmt.setInt(6, t.getExchangeId());
        stmt.setObject(6, null);

        stmt.setDate(7, java.sql.Date.valueOf(TickManager.getInstance().getDate()));

        stmt.execute();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        t.setId(auto_id);
    }

    @Override
    public ArrayList getAll() throws SQLException {
        PreparedStatement stmt = null;
        ArrayList<Operation> output = new ArrayList<>();
        stmt = getConnection().prepareStatement("SELECT * from \"operation\"");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add(getFromResultSet(rs));
        }
        return output;
    }
}
