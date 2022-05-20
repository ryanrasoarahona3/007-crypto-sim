package com.crypto.cryptosim;

import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.UserDAO;
import com.crypto.cryptosim.servlets.LoginServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginServletTest extends ServletBaseTest {

    User u;

    @BeforeEach
    public void init() throws SQLException {
        super.init();

        u = new User();
        u.setEmail("john@gmail.com");
        u.setPassword("password");
        UserDAO.getInstance().add(u);
    }

    @Test
    public void loginSuccessfully() throws ServletException, IOException {
        patchParameter("email", "john@gmail.com");
        patchParameter("password", "password");

        new LoginServlet().doPost(request, response);

        assertEquals(0, getErrorLen());
        assertThat(getRedirection(), containsString("dashboard"));
    }

    @Test
    public void incorrectPasswordLogin() throws ServletException, IOException {
        patchParameter("email", "john@gmail.com");
        patchParameter("password", "password2");

        new LoginServlet().doPost(request, response);

        assertEquals(dispatcher.resource, "login.jsp");
    }
}
