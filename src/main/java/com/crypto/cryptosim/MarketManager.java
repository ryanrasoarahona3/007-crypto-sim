package com.crypto.cryptosim;

import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MarketManager extends AbstractRepository{
    private static MarketManager instance = null;

    public static MarketManager getInstance(){
        if(instance == null)
            instance = new MarketManager();
        return instance;
    }

    @Override
    public void buildSQLTable() throws SQLException {
        String sql = "" +
                "" +
                "" +
                "CREATE TABLE \"crypto\" (\n" +
                "    crypto_id serial PRIMARY KEY,\n" +
                "    crypto_name varchar(255),\n" +
                "    crypto_slug varchar(255),\n" +
                "    crypto_desc text,\n" +
                "    crypto_seed INT,\n" +
                "    crypto_seed_cursor INT\n" +
                ");" +
                "" +
                "" +
                "CREATE TABLE \"price\" (\n" +
                "    price_crypto INT,\n" +
                "    price_date DATE NOT NULL,\n" +
                "    price_value INT,\n" +
                "    CONSTRAINT fk_crypto FOREIGN KEY (price_crypto) REFERENCES crypto(crypto_id)\n" +
                ");";
        Statement stmt = getConnection().createStatement();
        stmt.execute(sql);
    }

    @Override
    public void destroySQLTable() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.execute("DROP TABLE IF EXISTS \"price\";\n" +
                "DROP TABLE IF EXISTS \"crypto\";\n");
    }

    public ValuableCrypto getFromResultSet(ResultSet rs) throws SQLException {
        ValuableCrypto c = new ValuableCrypto();
        c.setId(rs.getInt("crypto_id"));
        c.setName(rs.getString("crypto_name"));
        c.setSlug(rs.getString("crypto_slug"));
        c.setDescription(rs.getString("crypto_desc"));
        c.setSeed(rs.getInt("crypto_seed"));
        try {
            rs.getInt("crypto_price");
            c.setValue(rs.getInt("crypto_price"));
        } catch (PSQLException e) {
            return c;
        }
        return c;
    }

    @Override
    public void add(Object _c) throws SQLException {
        // L'attribution de valeur à un Crypto se fait à partir de cette méthode
        ValuableCrypto c = (ValuableCrypto) _c;
        int seed = SemiRandomPriceManager.getInstance().requestForSeed();
        c.setSeed(seed);

        //
        PreparedStatement stmt = null;

        // Insertion dans la table crypto
        stmt = getConnection().prepareStatement("INSERT INTO \"crypto\" (crypto_name, crypto_slug, crypto_desc, crypto_seed) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, c.getName());
        stmt.setString(2, c.getSlug());
        stmt.setString(3, c.getDescription());
        stmt.setInt(4, c.getSeed());
        stmt.execute();
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        int auto_id = generatedKeys.getInt(1);
        c.setId(auto_id);

        // Insertion dans la table price
        stmt = getConnection().prepareStatement("INSERT INTO \"price\" (price_crypto, price_date, price_value) VALUES (?, ?, ?)");
        stmt.setInt(1, c.getId());
        stmt.setDate(2, java.sql.Date.valueOf(TickManager.getInstance().getDate()));
        stmt.setInt(3, c.getValue());
        stmt.execute();

        // Si l'ajout se passe bien, on enregistre parmi les observers
        TickManager.getInstance().addObserver(c);
    }


    public int getCryptoPrice(Crypto c) throws Exception {
        String sql = "select * from price where price_crypto=? order by price_date desc limit 1;";

        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, c.getId());
        ResultSet rs = stmt.executeQuery();
        if(!rs.next())
            throw new Exception("Le prix est introuvable, vérifiez que le prix a été initialisé");
        return rs.getInt("price_value");
    }

    @Override
    public ArrayList<ValuableCrypto> getAll() throws SQLException {
        String sql = "SELECT * FROM crypto";
        ArrayList<ValuableCrypto> output = new ArrayList<>();
        PreparedStatement stmt = getConnection().prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            ValuableCrypto c = getFromResultSet(rs);
            try {
                c.setValue(getCryptoPrice(c));
            } catch (Exception e) {
                e.printStackTrace();
            }
            output.add(c);
        }
        return output;

        /*
        String sql = "SELECT crypto_id, crypto_name, crypto_slug, crypto_desc, crypto_seed, price_value as crypto_price\n" +
                "FROM \"crypto\"\n" +
                "         INNER JOIN (\n" +
                "    SELECT price_crypto, max(price_date) as MaxDate FROM price group by price_crypto\n" +
                ") tm ON crypto_id = tm.price_crypto\n" +
                "         INNER JOIN price ON crypto_id = price.price_crypto;";
        PreparedStatement stmt = null;
        ArrayList<ValuableCrypto> output = new ArrayList<>();
        stmt = getConnection().prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            output.add((ValuableCrypto) getFromResultSet(rs));
        }
        return output;
         */
    }

    /**
     * Doit aussi inclure "cryptoBySlug"
     * @return
     */
    public ValuableCrypto cryptoByName(String name) throws SQLException {
        String sql = "SELECT crypto_id, crypto_name, crypto_slug, crypto_desc, crypto_seed, price_value as crypto_price\n" +
                "FROM \"crypto\"\n" +
                "         INNER JOIN (\n" +
                "    SELECT price_crypto, max(price_date) as MaxDate FROM price group by price_crypto\n" +
                ") tm ON crypto_id = tm.price_crypto\n" +
                "         INNER JOIN price ON crypto_id = price.price_crypto\n" +
                "WHERE crypto_name=? AND tm.MaxDate = price.price_date;";
        PreparedStatement stmt = null;
        stmt = getConnection().prepareStatement(sql);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next())
            return (ValuableCrypto) null;
        return getFromResultSet(rs);
    }
}
