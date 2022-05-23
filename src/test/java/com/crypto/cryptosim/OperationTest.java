package com.crypto.cryptosim;

import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.models.UserOperation;
import com.crypto.cryptosim.models.Wallet;
import com.crypto.cryptosim.models.WalletOperation;
import com.crypto.cryptosim.services.WalletOperationDAO;
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

        // Randomize a bit
        tm.nextTick();
        tm.nextTick();
    }

    @Test
    public void depositTest() throws SQLException {
        // test basique d'une opértation de dépot
        // John had done a deposit
        UserOperation o = new UserOperation();
        o.setDestination(u1.getId()); // The role of origin and destination is inverted compared to the old version
        o.setSum(1000);
        uod.add(o);

        ArrayList<UserOperation> operations = uod.getAll();
        assertEquals(1, operations.size());
    }

    @Test
    public void buyCryptoTest() throws SQLException {
        UserOperation o = new UserOperation();
        o.setDestination(u1.getId());
        o.setSum(1000);
        uod.add(o);

        WalletOperation w = new WalletOperation();
        w.setDestination(w1.getId());
        w.setN(2); // He as bought 2 unit of crypto
        w.setSum(2 * mm.cryptoById(w1.getCryptoId()).getValue());
        wod.add(w);

        ArrayList<WalletOperation> walletOperations = wod.getAll();
        assertEquals(1, walletOperations.size());
    }

    @Test
    public void checkBalanceAfterDepositAndWithdrawalOnlyTest() throws SQLException {
        om.deposit(u1, 1000);
        om.withdrawal(u1, 300);

        int balance = om.getBalance(u1);
        assertEquals(700, balance);
    }

    @Test
    public void buyingCryptoTest() throws SQLException {
        om.deposit(u1, 3000);

        assertEquals(u1.getId(), w1.getUserId());
        int btcPrice = mm.cryptoById(w1.getCryptoId()).getValue();
        om.buyCrypto(w1, 2);

        int balance = om.getBalance(u1);
        assertEquals(3000 - 2 * btcPrice, balance);
    }

    @Test
    public void sellingCryptoTest() throws SQLException {
        om.deposit(u1, 3000);

        assertEquals(u1.getId(), w1.getUserId());
        int btcPrice = mm.cryptoById(w1.getCryptoId()).getValue();
        om.buyCrypto(w1, 2);

        int balance = om.getBalance(u1);

        tm.nextTick();
        int btcNewPrice = mm.cryptoById(w1.getCryptoId()).getValue();
        om.sellCrypto(w1, 2);

        int newBalance = om.getBalance(u1);
        assertEquals(balance + 2*btcNewPrice, newBalance);
    }

    @Test
    public void numberOfCoinsTest() throws SQLException {
        om.deposit(u1, 5000);
        om.buyCrypto(w1, 2);
        tm.nextTick();
        om.buyCrypto(w1, 2);
        tm.nextTick();
        om.sellCrypto(w1, 1);

        int numberOfCoins = om.numberOfCoins(w1);
        assertEquals(3, numberOfCoins);
    }

    @Test
    public void walletOfJohnTest() throws SQLException {
        ArrayList<Wallet> walletsOfJohn = wd.walletsByUser(u1);
        assertEquals(3, walletsOfJohn.size());
    }
}
