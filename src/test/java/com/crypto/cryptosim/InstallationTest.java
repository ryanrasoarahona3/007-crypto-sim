package com.crypto.cryptosim;

import com.crypto.cryptosim.controllers.InstallationController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        InstallationController.getInstance().install(null);

        ValuableCrypto btc = MarketManager.getInstance().cryptoByName("Bitcoin");
        assertNotEquals(null, btc);
    }

    @AfterEach
    void tearDown(){
        // Use tearDown method to destroy Singleton instance
    }
}
