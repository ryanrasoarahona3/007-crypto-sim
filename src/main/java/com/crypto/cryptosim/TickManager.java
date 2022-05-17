package com.crypto.cryptosim;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Cette classe permet de gérer l'action du temps
 * Ce sera cette classe qui mettra à jour tous les valeurs des cryptos
 * À Chaque instant
 */
public class TickManager {
    private static TickManager instance = null;

    public static TickManager getInstance(){
        if(instance == null){
            instance = new TickManager();
        }
        return instance;
    }
    public static void tearDown(){
        TickManager.instance = null;
    }

    /**
     * Get Database connection
     * @return
     */
    protected Connection getConnection() throws SQLException {
        return DatabaseManager.getInstance().getConnection();
    }

    private ArrayList<ValuableCrypto> toBeUpdated = new ArrayList<>();

    /**
     * Permet de définir le changement de valeur apporté à un crypto
     * Pour notre cas, il s'agit uniquement des cryptos nouvellement
     * mis sur le marché
     * @param c
     */
    public void register(ValuableCrypto c){
        /**
         * Normalement, un crypto ne peut être ajouté qu'une seule fois
         */
        toBeUpdated.add(c);
    }


    public void nextTick() throws SQLException {

        date = date.plus(tick);
        /**
         * Doit être modifié pour prendre en compte tous les autres cryptos
         * déjà sur le marché
         */
        for(int i = 0; i < observers.size(); i++){
            ValuableCrypto c = observers.get(i);
            int newCursor = incrementCryptoCursor(c); // newCursor = c.cursor + 1
            SemiRandomPriceManager.getInstance().updateCryptoPrice(c, newCursor);

            // Register new price to database
            PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO \"price\" (price_crypto, price_date, price_value) VALUES (?, ?, ?);");
            stmt.setInt(1, c.getId());
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setInt(3, c.getValue());
            stmt.execute();
        }

        /*
        for(int i = 0; i < toBeUpdated.size(); i++){
            ValuableCrypto c = toBeUpdated.get(i);
            PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO \"price\" (price_crypto, price_date, price_value) VALUES (?, ?, ?)");
            stmt.setInt(1, c.getId());
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setInt(3, c.getValue());
            stmt.execute();

         */

            /*
            if(c.getSeed() == -1){
                // Il s'agit d'un crypto nouvellement créé
                int seed = SemiRandomPriceManager.getInstance().requestForSeed();
                c.setSeed(seed);
                // On met la table SQL à jour
                stmt = getConnection().prepareStatement("UPDATE \"crypto\" SET crypto_seed=? WHERE crypto_id=?");
                stmt.setInt(1, c.getSeed());
                stmt.setInt(2, c.getId());
                stmt.execute();
            }
            */
        /*
        }
         */

        /**
         * Si une nouvelle graine a été ajouté, on met la table à jour
         */

    }

    private static LocalDate fromString(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale( Locale.ENGLISH );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse(str);
        return date;
    }

    public static TemporalAmount tick = Period.ofDays(1);
    private LocalDate date = null;
    private ArrayList<ValuableCrypto> observers = new ArrayList<>();

    public LocalDate getDate() {
        return date;
    }

    public TickManager() {

        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement("SELECT price_date FROM price ORDER BY price_date DESC LIMIT 1;");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            date = fromString(rs.getDate("price_date").toString());
        } catch (SQLException e) {
            date = fromString("2000-01-01"); // date par défaut
        }
    }

    public void addObserver(ValuableCrypto c){
        observers.add(c);
    }

    public void removeObserver(ValuableCrypto c){
        observers.remove(c);
    }


    /**
     * Utilisé par l'algorithme
     * @param c
     * @return
     */
    public int getSeedCryptoCursor(ValuableCrypto c) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("SELECT crypto_seed_cursor FROM \"crypto\" WHERE crypto_id=?");
        stmt.setInt(1, c.getId());
        ResultSet rs = stmt.executeQuery();
        if(!rs.next())
            return -1;
        return rs.getInt("crypto_seed_cursor");
    }

    /**
     * Incrémenter la valeur du curseur
     * Pour l'intégrité de l'algorithme, seul tick manager permet de gérer
     * La propriété seed_cursor
     */
    private int incrementCryptoCursor(ValuableCrypto c) throws SQLException {
        int cursor = getSeedCryptoCursor(c);
        cursor+= 1;
        PreparedStatement stmt = getConnection().prepareStatement("UPDATE \"crypto\" SET crypto_seed_cursor=? WHERE crypto_id=?");
        stmt.setInt(1, cursor);
        stmt.setInt(2, c.getId());
        stmt.execute();
        return cursor;
    }
}
