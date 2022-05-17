package com.crypto.cryptosim;


import com.crypto.cryptosim.models.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TransactionTest extends AbstractTest{

    @Test
    void depositTest() throws SQLException {
        User u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        trm.deposit(u, 500);
        trm.deposit(u, 200);

        int balance = trm.getBalance(u);
        assertEquals( 700, balance);
    }

    @Test
    void balanceTest() throws SQLException {
        User u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        trm.deposit(u, 1000);
        trm.withdrawal(u, 300);

        int balance = trm.getBalance(u);
        assertEquals(700, balance);
    }

    @Test
    void buyCoinTest() throws SQLException {
        User u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        c.setValue(100);
        mm.add(c);

        trm.deposit(u, 1000);
        trm.buyCoin(u, c, 2);

        int remaining = trm.getBalance(u);
        assertEquals(1000 - 2*c.getValue(), remaining);
    }

    @Test
    void buyCoinAfterVariation() throws SQLException {
        User u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        c.setValue(100);
        mm.add(c);

        trm.deposit(u, 1000);
        tm.nextTick();
        assertNotEquals(100, c.getValue());
        trm.buyCoin(u, c, 2);

        int remaining = trm.getBalance(u);
        assertNotEquals(1000 - 2*100, remaining);
    }

    @Test
    void purchasedCoinNumber() throws SQLException {
        User u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        c.setValue(100);
        mm.add(c);

        trm.deposit(u, 1000);
        trm.buyCoin(u, c, 3);

        int purchased = trm.totalPurchased(u, c);
        assertEquals(3, purchased);
    }

    @Test
    void soldCoinNumber() throws SQLException {
        User u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        c.setValue(100);
        mm.add(c);

        trm.deposit(u, 1000);
        trm.buyCoin(u, c, 3);
        trm.sellCoin(u, c, 1);

        int sold = trm.totalSold(u, c);
        assertEquals(1, sold);
    }

    @Test
    void traderExampleTest() throws SQLException {
        User u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        ValuableCrypto c = new ValuableCrypto();
        c.setName("BTC");
        c.setValue(100);
        mm.add(c);

        trm.deposit(u, 1000);
        trm.buyCoin(u, c, 3);
        tm.nextTick();
        tm.nextTick();
        tm.nextTick();
        trm.sellCoin(u, c, 3);

        int balance = trm.getBalance(u);
        assertNotEquals(1000, balance);

        int numberOfCoin = trm.numberOfCoins(u, c);
        assertEquals(0, numberOfCoin);
    }
}
