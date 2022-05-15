package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractRepository;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.models.Gender;
import com.crypto.cryptosim.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class UserRepository extends AbstractRepository {
    private static UserRepository instance = null;
    public static UserRepository getInstance(){
        if(instance == null)
            instance = new UserRepository();
        return instance;
    }

    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS \"user\";\n" +
                "DROP TYPE IF EXISTS gender;\n" +
                "CREATE TYPE gender AS ENUM('MALE', 'FEMALE', 'UNKNOWN');\n" +
                "CREATE TABLE \"user\" (\n" +
                "                        user_id serial PRIMARY KEY,\n" +
                "                        user_email varchar(255),\n" +
                "                        user_pseudo varchar(255),\n" +
                "                        user_password varchar(255),\n" +
                "                        user_firstname varchar(255),\n" +
                "                        user_lastname varchar(255),\n" +
                "                        user_picture text,\n" +
                "                        user_birth date,\n" +
                "                        user_gender gender,\n" +
                "                        user_phone varchar(255),\n" +
                "                        user_address varchar(255)\n" +
                ");";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    // TODO: a factoriser
    private User getFromResultSet(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("user_id"));
        u.setEmail(rs.getString("user_email"));
        u.setPseudo(rs.getString("user_pseudo"));
        u.setPassword(rs.getString("user_password"));
        u.setFirstname(rs.getString("user_firstname"));
        u.setLastname(rs.getString("user_lastname"));
        u.setPicture(rs.getString("user_picture"));
        u.setBirth(new Date(rs.getDate("user_birth").getTime()));
        u.setGender(Gender.ofLabel(rs.getString("user_gender")));
        u.setPhone(rs.getString("user_phone"));
        u.setAddress(rs.getString("user_address"));
        return u;
    }

    @Override
    public void add(Object b) throws SQLException {
        User u = (User) b;
        String sql = "INSERT INTO \"user\" (user_email, user_pseudo, user_password, user_firstname, user_lastname, user_picture, user_birth, " +
                "user_gender, user_phone, user_address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, u.getEmail());
        stmt.setString(2, u.getPseudo());
        stmt.setString(3, u.getPassword());
        stmt.setString(4, u.getFirstname());
        stmt.setString(5, u.getLastname());
        stmt.setString(6, u.getPicture());

        if(u.getBirth() != null)
            stmt.setDate(7,
                java.sql.Date.valueOf(u.getBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        else
            stmt.setDate(7, null);

        if(u.getGender() != null)
            stmt.setObject(8, u.getGender(), java.sql.Types.OTHER);
        else
            stmt.setObject(8, Gender.UNKNOWN, java.sql.Types.OTHER);

        stmt.setString(9, u.getPhone());
        stmt.setString(10, u.getAddress());
        stmt.execute();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        u.setId(auto_id);
    }

    @Override
    public ArrayList getAll() throws SQLException {
        PreparedStatement stmt = null;
        ArrayList<User> output = new ArrayList<>();
        stmt = getConnection().prepareStatement("SELECT * from \"user\"");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add(getFromResultSet(rs));
        }
        return output;
    }

    public boolean verifyCredentials(User u) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM \"user\" WHERE user_email=? AND user_password=?");
        stmt.setString(1, u.getEmail());
        stmt.setString(2, u.getPassword());
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }
}
