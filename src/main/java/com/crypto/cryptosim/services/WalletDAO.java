package com.crypto.cryptosim.services;

import com.crypto.cryptosim.AbstractDAO;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.models.Wallet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class WalletDAO extends AbstractDAO<Wallet> {

    private static WalletDAO instance = null;
    public static WalletDAO getInstance(){
        if(instance == null)
            instance = new WalletDAO();
        return instance;
    }
    public static void tearDown() {
        WalletDAO.instance = null;
    }

    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "CREATE TABLE \"wallet\" (\n" +
                "    wallet_id serial PRIMARY KEY,\n" +
                "    wallet_name varchar(255),\n" +
                "    wallet_user int not null,\n" +
                "    wallet_crypto int not null,\n" +
                "    wallet_date \"date\",\n" +
                "    CONSTRAINT fk_user FOREIGN KEY (wallet_user) REFERENCES \"user\" (user_id),\n" +
                "    CONSTRAINT fk_crypto FOREIGN KEY (wallet_crypto) REFERENCES \"crypto\" (crypto_id)\n" +
                ")";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void destroySQLTable() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS \"wallet\";\n");
    }

    private Wallet getFromResultSet(ResultSet rs) throws SQLException {
        Wallet w = new Wallet();
        w.setId(rs.getInt("wallet_id"));
        w.setName(rs.getString("wallet_name"));
        w.setUserId(rs.getInt("wallet_user"));
        w.setCryptoId(rs.getInt("wallet_crypto"));
        w.setDate(rs.getDate("wallet_date").toLocalDate());
        return w;
    }

    @Override
    public void add(Wallet b) throws SQLException {
        Wallet w = (Wallet) b;
        String sql = "INSERT INTO \"wallet\" (wallet_name, wallet_user, wallet_crypto, wallet_date) VALUES (?, ?, ?, ?);";
        PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, b.getName());
        stmt.setInt(2, b.getUserId());
        stmt.setInt(3, b.getCryptoId());
        stmt.setDate(4, java.sql.Date.valueOf(TickManager.getInstance().getDate()));
        stmt.execute();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        w.setId(auto_id);
    }

    // TODO: UpdateDB
    // Pour l'instant, nous ne prenons pas en compte la modification de la base de données

    @Override
    public ArrayList<Wallet> getAll() throws SQLException {
        PreparedStatement stmt = null;
        ArrayList<Wallet> output = new ArrayList<>();
        stmt = getConnection().prepareStatement("SELECT * from \"wallet\"");
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add(getFromResultSet(rs));
        }
        return output;
    }
}
