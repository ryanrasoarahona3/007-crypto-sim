package com.crypto.cryptosim;

import com.crypto.cryptosim.models.User;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WalletTest extends AbstractTest {
    User u;
    User v;
    ValuableCrypto btc;
    ValuableCrypto eth;
    ValuableCrypto dog;
    ValuableCrypto bnb;

    @BeforeEach
    public void init() throws SQLException {
        super.init();

        u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        v = new User();
        v.setEmail("victor@gmail.com");
        ur.add(v);

        btc = new ValuableCrypto();
        btc.setName("BTC");
        btc.setSlug("BTC");
        btc.setValue(100);

        eth = new ValuableCrypto();
        eth.setName("ETH");
        eth.setSlug("ETH");
        eth.setValue(150);

        dog = new ValuableCrypto();
        dog.setName("DOG");
        dog.setSlug("DOG");
        dog.setValue(200);

        bnb = new ValuableCrypto();
        bnb.setName("BNB");
        bnb.setSlug("BNB");
        bnb.setValue(200);

        mm.add(btc);
        mm.add(eth);
        mm.add(dog);
        mm.add(bnb);
    }

    @Test
    public void possessedCryptoValues() throws SQLException {
        trm.buyCoin(u, btc, 1); // 100
        trm.buyCoin(u, eth, 2); // 300
        trm.buyCoin(u, dog, 1); // 200
        assertEquals(600, trm.totalCryptoValues(u));

        tm.nextTick();
        assertNotEquals(500, trm.totalCryptoValues(u));
    }

    @Test
    public void oneCryptoTrend() throws SQLException {
        for(int i = 0; i < 30; i++)
            tm.nextTick();
        float wt = trm.getWeeklyTrend(btc);
        // A vérifier manuellement
        assertNotEquals(0, wt);
    }

    @Test
    public void testTest() throws SQLException {
        trm.deposit(u, 3500);
        trm.buyCoin(u, btc, 1); // 100
        trm.buyCoin(u, eth, 2); // 300
        trm.buyCoin(u, dog, 1); // 200
        trm.deposit(v, 1000);
        trm.buyCoin(v, btc, 5);


        for(int i = 0; i < 5; i++) tm.nextTick();

        trm.buyCoin(u, btc, 2);
        trm.sellCoin(u, eth, 1);
        trm.withdrawal(u, 200);
        trm.sellCoin(v, btc, 5);

        ArrayList<ArrayList<String>> d = trm.getTransactionDetails(u);
        //System.out.println("");

        assertEquals(7, d.size());
    }

    /*
    @Test
    public void cryptoactifTotalTest() {

        ValuableCrypto

        assertEquals(true, true);
    }
     */
}
