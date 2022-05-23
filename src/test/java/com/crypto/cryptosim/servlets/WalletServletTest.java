package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.ServletBaseTest;
import com.crypto.cryptosim.ValuableCrypto;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.models.Wallet;
import com.crypto.cryptosim.services.UserDAO;
import com.crypto.cryptosim.services.WalletDAO;
import com.crypto.cryptosim.structures.Info;
import com.crypto.cryptosim.structures.InputError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WalletServletTest extends ServletBaseTest {
    private WalletServlet s;
    User u;
    ValuableCrypto c1, c2;
    Wallet w1, w2;

    @BeforeEach
    public void init() throws SQLException {
        super.init();

        u = new User();
        u.setEmail("john@gmail.com");
        u.setPassword("password");
        UserDAO.getInstance().add(u);

        c1 = new ValuableCrypto();
        c1.setName("Bitcoin");
        c1.initValue(1000);
        mm.add(c1);

        c2 = new ValuableCrypto();
        c2.setName("Eutherium");
        c2.initValue(500);
        mm.add(c2);

        w1 = new Wallet();
        w1.setName("First wallet");
        w1.setUserId(u.getId());
        w1.setCryptoId(c1.getId());
        wd.add(w1);

        w2 = new Wallet();
        w2.setName("Second wallet of john");
        w2.setUserId(u.getId());
        w2.setCryptoId(c2.getId());
        wd.add(w2);

        tm.nextTick();
        om.deposit(u, 5000);

        s = new WalletServlet();
    }

    @Test
    public void succesfulInsertionTest() throws ServletException, IOException, SQLException {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());

        patchParameter("action", "create_wallet");
        patchParameter("wallet_name", "My First Wallet");
        patchParameter("wallet_crypto", ""+c1.getId());

        s.doPost(request, response);
        assertEquals(0, s.getErrorLen());
        assertTrue(s.haveInfo(Info.WALLET_CREATED));

        ArrayList<Wallet> wallets = WalletDAO.getInstance().getAll();
        assertEquals(3, wallets.size()); // Normally, it is 2
    }

    @Test
    public void createWalletEmptyNameProvided() throws ServletException, IOException {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());

        patchParameter("action", "create_wallet");
        patchParameter("wallet_name", "");
        patchParameter("wallet_crypto", ""+c1.getId());
        s.doPost(request, response);

        assertEquals(1, s.getErrorLen());
        assertTrue(s.haveInputError(InputError.WALLET_EMPTY_NAME));
    }

    @Test
    public void buyCryptoTest() throws ServletException, IOException, SQLException {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());

        patchParameter("action", "buy_crypto");
        patchParameter("wallet", ""+w1.getId());
        patchParameter("n", ""+2);

        int balance = om.getBalance(u);
        int unitPrice = mm.cryptoById(w1.getCryptoId()).getValue();
        s.doPost(request, response);
        assertEquals(0, s.getErrorLen());
        assertTrue(s.haveInfo(Info.WALLET_TRANSACTION_DONE));
        assertEquals(balance - 2*unitPrice, om.getBalance(u));

    }

    @Test
    public void buyTooMuchCrytoTest() throws ServletException, IOException {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());

        patchParameter("action", "buy_crypto");
        patchParameter("wallet", ""+w1.getId());
        patchParameter("n", ""+200);

        s.doPost(request, response);
        assertEquals(1, s.getErrorLen());
        assertTrue(s.haveInputError(InputError.WALLET_NOT_ENOUGH_BALANCE));
    }
}
