package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.ServletBaseTest;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.TransactionManager;
import com.crypto.cryptosim.services.UserDAO;
import com.crypto.cryptosim.structures.Info;
import com.crypto.cryptosim.structures.InputError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SettingServletTest extends ServletBaseTest {

    private SettingsServlet s;
    User u;


    @BeforeEach
    public void init() throws SQLException {
        super.init();
        u = new User();
        u.setEmail("john@gmail.com");
        u.setPassword("password");
        UserDAO.getInstance().add(u);

        TransactionManager.getInstance().deposit(u, 1000);
        s = new SettingsServlet();
    }

    @Test
    public void expiredPostSessionTest() throws IOException {

        s.doPost(request, response);
        assertEquals(1, s.getErrorLen());
        assertTrue(s.haveInputError(InputError.SESSION_EXPIRED));
        assertThat(getRedirection(), containsString("login"));
    }

    @Test
    public void incorrectPasswordTest() throws IOException {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());
        patchParameter("existingPassword", "incorrectPass");
        patchParameter("newPassword", "mynewpassword");
        patchParameter("newPasswordConfirm", "mynewpassword");
        s.doPost(request, response);
        assertEquals(1, s.getErrorLen());
        assertTrue(s.haveInputError(InputError.PASSUPDATE_INCORRECT_PASSWORD));
        assertThat(getRedirection(), containsString("settings"));
    }

    @Test
    public void incorrectPassConfirm() throws IOException {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());
        patchParameter("existingPassword", u.getPassword());
        patchParameter("newPassword", "mynewpassword");
        patchParameter("newPasswordConfirm", "mynewpassworn");
        s.doPost(request, response);
        assertEquals(1, s.getErrorLen());
        assertTrue(s.haveInputError(InputError.PASSUPDATE_PASSWORD_MISMATCHED));
        assertThat(getRedirection(), containsString("settings"));
    }

    @Test
    public void successfulUpdatePassword() throws IOException, SQLException {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());
        patchParameter("existingPassword", u.getPassword());
        patchParameter("newPassword", "mynewpassword");
        patchParameter("newPasswordConfirm", "mynewpassword");
        s.doPost(request, response);
        assertEquals(0, s.getErrorLen());
        assertTrue(s.haveInfo(Info.PASSUPDATE_SUCCESS));
        assertThat(getRedirection(), containsString("login"));

        User _u = UserDAO.getInstance().searchByEmail(u.getEmail());
        assertEquals(_u.getPassword(), "mynewpassword");
    }
}
