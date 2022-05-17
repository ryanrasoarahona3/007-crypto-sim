package com.crypto.cryptosim;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleCryptoTest extends AbstractTest{

    @Test
    void getGryptoListTestAfterManyTicks() throws SQLException {
        ValuableCrypto c1 = new ValuableCrypto();
        c1.setName("BTC");
        c1.setValue(200);
        mm.add(c1);

        ValuableCrypto c2 = new ValuableCrypto();
        c2.setName("ETH");
        c2.setValue(50);
        mm.add(c2);

        for(int i = 0; i < 5; i++)
            tm.nextTick();

        ArrayList<ValuableCrypto> cryptoList = mm.getAll();
        assertEquals(2, cryptoList.size());
    }

}
