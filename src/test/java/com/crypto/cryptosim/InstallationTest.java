package com.crypto.cryptosim;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class InstallationTest {
    private DatabaseManager dm;
    private TickManager tm;
    private MarketManager mm;

    @BeforeEach
    void init() throws SQLException{
        dm = DatabaseManager.getInstance();
        dm.setDbName("crypto-test");
        dm.init();

        tm = TickManager.getInstance();
        mm = MarketManager.getInstance();
        mm.buildSQLTable();
    }

    @Test
    void installationTest() throws SQLException{

    }
}
