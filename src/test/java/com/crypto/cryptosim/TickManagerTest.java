package com.crypto.cryptosim;

import com.crypto.cryptosim.models.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TickManagerTest extends AbstractTest{

    @Test
    public void getDate() throws SQLException {
        User u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        tm.nextTick();
        tm.nextTick();
        LocalDate date = tm.getDate();
        assertEquals(3, date.getDayOfMonth());
    }

    @Test
    public void initialDateTest() throws SQLException {

        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        c.setValue(100);
        mm.add(c);
        assertEquals("2000-01-01", tm.getDate().toString());

        tm.nextTick();
        tm.nextTick();

        assertEquals("2000-01-03", tm.getDate().toString());
        TickManager.tearDown();
        tm = TickManager.getInstance();
        assertEquals("2000-01-03", tm.getDate().toString());

    }

    @Test
    public void initialCryptoValue() throws SQLException {

        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        c.setValue(100);
        mm.add(c);
        assertEquals("2000-01-01", tm.getDate().toString());

        tm.nextTick();
        tm.nextTick();

        assertEquals("2000-01-03", tm.getDate().toString());
        TickManager.tearDown();
        tm = TickManager.getInstance();

        ValuableCrypto _c = mm.cryptoByName("BTC");
        System.out.println("");
        assertNotEquals(100, c.getValue());

    }
}
