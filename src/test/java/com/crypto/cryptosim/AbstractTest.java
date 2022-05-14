package com.crypto.cryptosim;

import java.sql.SQLException;

public abstract class AbstractTest {
    protected DatabaseManager dm;
    protected TickManager tm;
    protected MarketManager mm;

    protected void init() throws SQLException {
        dm = DatabaseManager.getInstance();
        dm.setDbName("crypto-test");
        dm.init();
        tm = TickManager.getInstance();
        mm = MarketManager.getInstance();
        mm.buildSQLTable();
    }
}
