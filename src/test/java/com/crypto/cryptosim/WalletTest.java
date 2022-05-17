package com.crypto.cryptosim;

import com.crypto.cryptosim.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletTest extends AbstractTest {
    User u;
    ValuableCrypto c;
    @BeforeEach
    public void init() throws SQLException {
        super.init();

        u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        c = new ValuableCrypto();
        c.setName("BTC");
        c.setValue(100);
    }

    @Test
    public void possessedCrypto() {


    }

    /*
    @Test
    public void cryptoactifTotalTest() {

        ValuableCrypto

        assertEquals(true, true);
    }
     */
}
