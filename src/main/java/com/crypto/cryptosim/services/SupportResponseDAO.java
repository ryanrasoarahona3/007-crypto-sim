package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;
import com.crypto.cryptosim.models.ReferencedSupportRequest;
import com.crypto.cryptosim.models.ReferencedSupportResponse;

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
        r.setId(rs.getInt("request_id"));
        r.setTitle(rs.getString("request_title"));
        r.setMessage(rs.getString("request_message"));
        r.setUserId(rs.getInt("request_user"));
        r.setEmail(rs.getString("user_email"));
        return r; // WE ARE HERE
    }

    @Override
    public void add(Object b) throws SQLException {

    }

    @Override
    public ArrayList getAll() throws SQLException {
        return null;
    }
}
