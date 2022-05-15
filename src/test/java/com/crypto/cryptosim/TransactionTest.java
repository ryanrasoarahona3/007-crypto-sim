package com.crypto.cryptosim;


import com.crypto.cryptosim.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
