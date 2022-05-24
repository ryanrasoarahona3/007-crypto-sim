package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;
import com.crypto.cryptosim.models.ReferencedSupportRequest;
import com.crypto.cryptosim.models.ReferencedSupportResponse;
import com.crypto.cryptosim.models.SupportRequest;
import com.crypto.cryptosim.models.SupportResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SupportResponseDAO extends AbstractDAO {
    private static SupportResponseDAO instance = null;
    public static SupportResponseDAO getInstance(){
        if(instance == null)
            instance = new SupportResponseDAO();
        return instance;
    }
    public static void tearDown(){
        SupportResponseDAO.instance = null;
    }

    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "CREATE TABLE \"supportResponse\" (\n" +
                "    response_id serial PRIMARY KEY,\n" +
                "    response_title varchar(255),\n" +
                "    response_message text,\n" +
                "    response_user int not null,\n" +
                "    CONSTRAINT fk_user FOREIGN KEY (response_user) REFERENCES \"user\" (user_id)\n" +
                ");";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void destroySQLTable() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS \"supportResponse\";");
    }

    private ReferencedSupportResponse getFromResultSet(ResultSet rs) throws SQLException {
        ReferencedSupportResponse r = new ReferencedSupportResponse();
        r.setId(rs.getInt("response_id"));
        r.setTitle(rs.getString("response_title"));
        r.setMessage(rs.getString("response_message"));
        r.setUserId(rs.getInt("response_user"));
        r.setEmail(rs.getString("user_email"));
        return r;
    }

    @Override
    public void add(Object b) throws SQLException {
        SupportResponse r = (SupportResponse) b;
        String sql = "INSERT INTO \"supportResponse\" (response_title, response_message, response_user) VALUES (?, ?, ?);";
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
        ArrayList<SupportResponse> output = new ArrayList<>();
        stmt = getConnection().prepareStatement("SELECT * from \"supportResponse\" INNER JOIN \"user\" ON response_user = \"user\".\"user_id\"");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add(getFromResultSet(rs));
        }
        return output;
    }
}
