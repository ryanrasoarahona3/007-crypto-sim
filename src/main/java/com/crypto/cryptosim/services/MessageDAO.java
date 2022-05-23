package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;
import com.crypto.cryptosim.models.Message;
import com.crypto.cryptosim.models.ReferencedMessage;

import java.sql.*;
import java.util.ArrayList;

/**
 * @deprecated Not in use, use SupportRequest Instead
 */
public class MessageDAO extends AbstractDAO {
    private static MessageDAO instance = null;
    public static MessageDAO getInstance(){
        if(instance == null)
            instance = new MessageDAO();
        return instance;
    }
    public static void tearDown(){
        MessageDAO.instance = null;
    }


    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "CREATE TABLE \"message\" (\n" +
                "    message_id serial PRIMARY KEY,\n" +
                "    message_request varchar(255),\n" +
                "    message_title varchar(255),\n" +
                "    message_body text,\n" +
                "    message_sender INT NOT NULL\n," +
                "    CONSTRAINT fk_sender FOREIGN KEY (message_sender) REFERENCES \"user\"(\"user_id\")\n" +
                ")";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void destroySQLTable() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS \"message\";\n");
    }

    private Message getFromResultSet(ResultSet rs) throws SQLException {
        Message m = new ReferencedMessage();
        m.setId(rs.getInt("message_id"));
        m.setRequest(rs.getString("message_request"));
        m.setTitle(rs.getString("message_title"));
        m.setBody(rs.getString("message_body"));
        m.setSenderId(rs.getInt("message_sender"));
        return m;
    }

    @Override
    public void add(Object b) throws SQLException {
        Message m = (Message)b;
        String sql = "INSERT INTO \"message\" (message_request, message_title, message_body, message_sender) VALUES" +
                "(?, ?, ?, ?)";
        PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, m.getRequest());
        stmt.setString(2, m.getTitle());
        stmt.setString(3, m.getBody());
        stmt.setInt(4, m.getSenderId());
        stmt.execute();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        m.setId(auto_id);
    }

    @Override
    public ArrayList getAll() throws SQLException {
        PreparedStatement stmt = null;
        ArrayList<Message> output = new ArrayList<>();
        stmt = getConnection().prepareStatement("SELECT * from \"message\"");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add(getFromResultSet(rs));
        }
        return output;
    }

    public ArrayList<ReferencedMessage> getTableData() throws SQLException {
        String sql = "SELECT * FROM message INNER JOIN \"user\" ON message_sender=\"user\".\"user_id\";";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        ArrayList<ReferencedMessage> output = new ArrayList<>();
        while(rs.next()){
            ReferencedMessage row = (ReferencedMessage)getFromResultSet(rs);
            row.setEmail(rs.getString("user_email"));
            output.add(row);
        }
        return output;
    }
}
