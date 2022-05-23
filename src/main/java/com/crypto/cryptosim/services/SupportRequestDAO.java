package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;
import com.crypto.cryptosim.models.Message;
import com.crypto.cryptosim.models.ReferencedSupportRequest;
import com.crypto.cryptosim.models.SupportRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SupportRequestDAO extends AbstractDAO {
    private static SupportRequestDAO instance = null;
    public static SupportRequestDAO getInstance(){
        if(instance == null)
            instance = new SupportRequestDAO();
        return instance;
    }
    public static void tearDown(){
        SupportRequestDAO.instance = null;
    }


    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "CREATE TABLE \"supportRequest\" (\n" +
                "    request_id serial PRIMARY KEY,\n" +
                "    request_title varchar(255),\n" +
                "    request_message text,\n" +
                "    request_user int not null,\n" +
                "    CONSTRAINT fk_user FOREIGN KEY (request_user) REFERENCES \"user\" (user_id)\n" +
                ");";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void destroySQLTable() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS \"supportRequest\";");
    }

    private ReferencedSupportRequest getFromResultSet(ResultSet rs) throws SQLException {
        ReferencedSupportRequest r = new ReferencedSupportRequest();
        r.setId(rs.getInt("request_id"));
        r.setTitle(rs.getString("request_title"));
        r.setMessage(rs.getString("request_message"));
        r.setUserId(rs.getInt("request_user"));
        r.setEmail(rs.getString("user_email"));
        return r;
    }

    @Override
    public void add(Object b) throws SQLException {
        SupportRequest r = (SupportRequest) b;
        String sql = "INSERT INTO \"supportRequest\" (request_title, request_message, request_user) VALUES (?, ?, ?);";
        PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, r.getTitle());
        stmt.setString(2, r.getMessage());
        stmt.setInt(3, r.getUserId());
        stmt.execute();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        r.setId(auto_id);
    }

    @Override
    public ArrayList getAll() throws SQLException {
        PreparedStatement stmt = null;
        ArrayList<SupportRequest> output = new ArrayList<>();
        stmt = getConnection().prepareStatement("SELECT * from \"supportRequest\" INNER JOIN \"user\" ON request_user = \"user\".\"user_id\"");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add(getFromResultSet(rs));
        }
        return output;
    }
}
