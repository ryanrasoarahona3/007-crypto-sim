package com.crypto.cryptosim;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MarketTest {
    private DatabaseManager dm;
    private TickManager tm;
    private MarketManager mm;

    @BeforeEach
    void init() throws SQLException {

        dm = DatabaseManager.getInstance();
        dm.setDbName("crypto-test");
        dm.init();

        tm = TickManager.getInstance();

        mm = MarketManager.getInstance();
        mm.buildSQLTable();
    }

    @Test
    void cryptoInsertion() throws SQLException {
        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        c.initValue(1000);
        mm.add(c);

        ArrayList<ValuableCrypto> list = mm.getAll();
        assertNotEquals(0, list.size());
    }

    @Test
    void cryptoDoubleInsertion() throws SQLException{
        ValuableCrypto c1 = new ValuableCrypto();
        c1.setName("BTC");
        c1.initValue(1000);
        mm.add(c1);

        ValuableCrypto c2 = new ValuableCrypto();
        c2.setName("ETH");
        c2.initValue(200);
        mm.add(c2);

        ValuableCrypto _c1 = mm.cryptoByName("BTC");
        assertEquals(1000, _c1.getValue());

        ValuableCrypto _c2 = mm.cryptoByName("ETH");
        assertEquals(200, _c2.getValue());
    }

    @Test
    void cryptoCursorTest() throws SQLException {
        ValuableCrypto c1 = new ValuableCrypto();
        c1.setName("BTC");
        c1.initValue(1000);
        mm.add(c1);

        int cursor = tm.getSeedCryptoCursor(c1);
        assertEquals(0, cursor);
    }

    @Test
    void cryptoCursorIncrementation() throws SQLException {
        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        c.initValue(1000);
        mm.add(c);

        tm.nextTick();
        assertEquals(1, tm.getSeedCryptoCursor(c));
    }

    @Test
    void cryptoCursorValueVariation() throws SQLException {
        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        c.initValue(1000);
        mm.add(c);

        tm.nextTick();
        assertNotEquals(1000, c.getValue());

        // Retrieve it from database
        ValuableCrypto _c = mm.cryptoByName("BTC");
        assertEquals(c.getValue(), _c.getValue());
    }

    @Test
    void twoCryptoInstallation() throws SQLException {
        ValuableCrypto c1 = new ValuableCrypto();
        c1.setName("BTC");
        c1.setValue(200);
        mm.add(c1);

        ValuableCrypto c2 = new ValuableCrypto();
        c2.setName("ETH");
        c2.setValue(50);
        mm.add(c2);

        assertNotEquals(-1, c1.getSeed());
        assertNotEquals(-1, c2.getSeed());
    }

    @AfterEach
    void tearDown(){
        SemiRandomPriceManager.tearDown();
        TickManager.tearDown();
    }

}
