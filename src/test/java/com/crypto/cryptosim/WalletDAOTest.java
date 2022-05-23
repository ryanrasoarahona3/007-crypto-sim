package com.crypto.cryptosim;

import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.models.Wallet;
import com.crypto.cryptosim.services.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class WalletDAOTest extends BaseTest{

    User u;
    ValuableCrypto c;

    @BeforeEach
    public void init() throws SQLException {
        super.init();

        u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        c = new ValuableCrypto();
        c.setName("Bitcoin");
        c.initValue(1000);
        mm.add(c);

        tm.nextTick();
    }

    @Test
    public void insertionTest() throws SQLException {
        Wallet w = new Wallet();
        w.setName("The name");
        w.setUserId(u.getId());
        w.setCryptoId(c.getId());
        // A bit long, but it works=
        w.setDate(tm.getDate());
        wd.add(w);

        ArrayList<Wallet> ws = wd.getAll();
        assertEquals(1, ws.size());
    }
}
