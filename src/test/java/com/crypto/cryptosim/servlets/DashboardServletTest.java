package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.ServletBaseTest;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.TransactionManager;
import com.crypto.cryptosim.services.UserDAO;
import com.crypto.cryptosim.structures.Info;
import com.crypto.cryptosim.structures.InputError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DashboardServletTest extends ServletBaseTest {

    private DashboardServlet s;
    User u;

    @BeforeEach
    public void init() throws SQLException {
        super.init();
        u = new User();
        u.setEmail("john@gmail.com");
        u.setPassword("password");
        UserDAO.getInstance().add(u);

        TransactionManager.getInstance().deposit(u, 1000);

        s = new DashboardServlet();
    }

    @Test
    public void expiredSessionTest() throws ServletException, IOException {

        s.doGet(request, response);
        assertEquals(1, s.getErrorLen());
        assertTrue(s.haveInputError(InputError.SESSION_EXPIRED));
        assertThat(getRedirection(), containsString("login"));
    }

    @Test
    public void successfulSession() throws ServletException, IOException {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());
        assertEquals(0, s.getErrorLen());
    }

    @Test
    public void successfulDeposit() {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());

        patchParameter("action", "deposit");
        patchParameter("sum", "500");
        s.doPost(request, response);
        assertEquals(0, s.getErrorLen());
    }

    @Test
    public void successfulWidthdrawal() {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());

        patchParameter("action", "withdrawal");
        patchParameter("sum", "500");
        s.doPost(request, response);
        assertEquals(0, s.getErrorLen());
    }

    @Test
    public void insufficientBalanceError() {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());

        patchParameter("action", "withdrawal");
        patchParameter("sum", "1100");
        s.doPost(request, response);
        assertEquals(1, s.getErrorLen());
        assertTrue(s.haveInputError(InputError.WITHDRAWAL_INSUFFICIENT_BALANCE));
    }


}
