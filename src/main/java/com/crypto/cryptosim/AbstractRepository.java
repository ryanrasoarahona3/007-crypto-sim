package com.crypto.cryptosim;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class AbstractRepository<Bean> {

    /**
     * Get Database connection
     * @return
     */
    protected Connection getConnection(){
        return DatabaseManager.getInstance().getConnection();
    }

    /**
     * Build SQL Table structure inside database
     * @throws SQLException
     */
    public abstract void buildSQLTable() throws SQLException;

    /**
     * Add an entity
     * @param b The entity to append
     * @throws SQLException
     */
    public abstract void add(Bean b) throws SQLException;

    /**
     * Get List
     * @return The list retrieved from the database
     * @throws SQLException
     */
    public abstract ArrayList<Bean> getAll() throws SQLException;
}
