package com.crypto.cryptosim;

import com.crypto.cryptosim.models.Operation;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.models.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OperationTest extends BaseTest {

    User u1;
        Wallet w1;
        Wallet w2;
        Wallet w3;

    User u2;
        Wallet w4;
        Wallet w5;

    ValuableCrypto btc;
    ValuableCrypto eth;

    @BeforeEach
    public void init() throws SQLException {
        super.init();

        u1 = new User();
        u1.setEmail("john@gmail.com");
        ur.add(u1);

        u2 = new User();
        u2.setEmail("jane@gmail.com");
        ur.add(u2);

        btc = new ValuableCrypto();
        btc.setName("Bitcoin");
        btc.initValue(1000);
        mm.add(btc);

        eth = new ValuableCrypto();
        eth.setName("Euthérium");
        eth.initValue(500);
        mm.add(eth);

        w1 = new Wallet();
        w1.setName("John's first wallet");
        w1.setUserId(u1.getId());
        w1.setCryptoId(btc.getId());
        w1.setDate(tm.getDate());
        wd.add(w1);

        w2 = new Wallet();
        w2.setName("John's second wallet - a bitcoin wallet");
        w2.setUserId(u1.getId());
        w2.setCryptoId(btc.getId());
        w2.setDate(tm.getDate());
        wd.add(w2);

        w3 = new Wallet();
        w3.setName("John's a third wallet - an euthérium wallet");
        w3.setUserId(u1.getId());
        w3.setCryptoId(eth.getId());
        w3.setDate(tm.getDate());
        wd.add(w3);

        w4 = new Wallet();
        w4.setName("Jane's bitcoin wallet");
        w4.setUserId(u2.getId());
        w4.setCryptoId(btc.getId());
        w4.setDate(tm.getDate());
        wd.add(w4);

        w5 = new Wallet();
        w5.setName("Jane's euthérium wallet");
        w5.setUserId(u2.getId());
        w5.setCryptoId(eth.getId());
        w5.setDate(tm.getDate());
        wd.add(w5);
    }

    @Test
    public void depositTest() throws SQLException {
        // test basique d'une opértation de dépot
        // John had done a deposit
        Operation o = new Operation();
        o.setOrigin(u1.getId());
        o.setSum(1000);
        od.add(o);

        ArrayList<Operation> operations = od.getAll();
        assertEquals(1, operations.size());
    }
}
