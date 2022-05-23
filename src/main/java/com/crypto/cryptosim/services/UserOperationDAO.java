package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.models.Transaction;
import com.crypto.cryptosim.models.UserOperation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserOperationDAO extends AbstractDAO {
    private static UserOperationDAO instance = null;
    public static UserOperationDAO getInstance(){
        if(instance == null)
            instance = new UserOperationDAO();
        return instance;
    }
    public static void tearDown(){
        UserOperationDAO.instance = null;
    }

    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "CREATE TABLE \"user_operation\" (\n" +
                "    user_operation_id serial PRIMARY KEY,\n" +
                "    user_operation_origin int null, -- This is a user\n" +
                "    user_operation_destination int null, -- Same same\n" +
                "    user_operation_sum int,\n" +
                "    user_operation_date date not null,\n" +
                "    CONSTRAINT fk_origin FOREIGN KEY (user_operation_origin) REFERENCES \"user\"(user_id),\n" +
                "    CONSTRAINT fk_destination FOREIGN KEY (user_operation_destination) REFERENCES \"user\"(user_id)\n" +
                ")";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void destroySQLTable() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS \"user_operation\";\n");
    }

    private UserOperation getFromResultSet(ResultSet rs) throws SQLException {
        UserOperation o = new UserOperation();
        o.setId(rs.getInt("user_operation_id"));
        o.setOrigin(rs.getInt("user_operation_origin"));
        o.setDestination(rs.getInt("user_operation_destination"));
        o.setSum(rs.getInt("user_operation_sum"));
        //o.setDate(rs.getDate("operation_date").toLocalDate());
        return o;
    }

    @Override
    public void add(Object b) throws SQLException {
        UserOperation t = (UserOperation) b;
        String sql = "INSERT INTO \"user_operation\" (user_operation_origin, user_operation_destination, " +
                "user_operation_sum,  user_operation_date) VALUES (?, ?, ?, ?);";
        PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        if(t.getOrigin() != 0) stmt.setInt(1, t.getOrigin());
        else stmt.setObject(1, null);

        if(t.getDestination() != 0) stmt.setInt(2, t.getDestination());
        else stmt.setObject(2, null);

        stmt.setInt(3, t.getSum()); // Une valeur obligatoire

        stmt.setDate(4, java.sql.Date.valueOf(TickManager.getInstance().getDate()));

        stmt.execute();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        t.setId(auto_id);
    }

    @Override
    public ArrayList getAll() throws SQLException {
        PreparedStatement stmt = null;
        ArrayList<UserOperation> output = new ArrayList<>();
        stmt = getConnection().prepareStatement("SELECT * from \"user_operation\"");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add(getFromResultSet(rs));
        }
        return output;
    }
}
