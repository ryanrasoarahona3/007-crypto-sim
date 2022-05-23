package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.models.UserOperation;
import com.crypto.cryptosim.models.WalletOperation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WalletOperationDAO extends AbstractDAO {
    private static WalletOperationDAO instance = null;
    public static WalletOperationDAO getInstance(){
        if(instance == null)
            instance = new WalletOperationDAO();
        return instance;
    }
    public static void tearDown(){
        WalletOperationDAO.instance = null;
    }


    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "CREATE TABLE \"wallet_operation\" (\n" +
                "    wallet_operation_id serial PRIMARY KEY,\n" +
                "    wallet_operation_origin int null, --\n" +
                "    wallet_operation_destination int null, --\n" +
                "    wallet_operation_sum int null,\n" +
                "    wallet_operation_date date not null,\n" +
                "    CONSTRAINT fk_origin FOREIGN KEY (wallet_operation_origin) REFERENCES \"wallet\"(wallet_id),\n" +
                "    CONSTRAINT fk_destination FOREIGN KEY (wallet_operation_destination) REFERENCES \"wallet\"(wallet_id)\n" +
                ")";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void destroySQLTable() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS \"wallet_operation\";\n");
    }

    @Override
    public void add(Object b) throws SQLException {

        WalletOperation t = (WalletOperation) b;
        String sql = "INSERT INTO \"user_operation\" (wallet_operation_origin, wallet_operation_destination, " +
                "wallet_operation_n, user_operation_sum,  user_operation_date) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        if(t.getOrigin() != 0) stmt.setInt(1, t.getOrigin());
        else stmt.setObject(1, null);

        if(t.getDestination() != 0) stmt.setInt(2, t.getDestination());
        else stmt.setObject(2, null);

        stmt.setInt(3, t.getN());

        stmt.setInt(4, t.getSum()); // Une valeur obligatoire

        stmt.setDate(5, java.sql.Date.valueOf(TickManager.getInstance().getDate()));

        stmt.execute();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        t.setId(auto_id);
    }

    @Override
    public ArrayList getAll() throws SQLException {
        return null;
    }
}
