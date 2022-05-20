package com.crypto.cryptosim;

import com.crypto.cryptosim.mockers.FakeError;
import com.crypto.cryptosim.mockers.HttpServletRequestMocker;
import com.crypto.cryptosim.mockers.HttpServletResponseMocker;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.UserDAO;
import com.crypto.cryptosim.servlets.PasswordModificationServlet;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assert.*;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class PasswordModificationServletTest extends ServletBaseTest {

    //private HttpServletRequestMocker request;
    //private HttpServletResponseMocker response;
    private User u;
    private Exception exception;

    // En appliquant les standards sur les phases de tests

    @BeforeEach
    public void init() throws SQLException {
        super.init();

        u = new User();
        u.setEmail("john@gmail.com");
        u.setPassword("password");
        UserDAO.getInstance().add(u);

        patchSession("email", "john@gmail.com");
        patchSession("password", "password");

    }

    @Test
    public void passwordModificationWorksFine() throws IOException {
        when(request.getParameter("existingPassword")).thenReturn("password");
        when(request.getParameter("newPassword")).thenReturn("newpass");
        when(request.getParameter("newPasswordConfirm")).thenReturn("newpass");

        patchParameter("existingPassword", "password");
        patchParameter("newPassword", "newpass");
        patchParameter("newPasswordConfirm", "newpass");

        new PasswordModificationServlet().doPost(request, response);

        assertEquals(0, ((HttpServletResponseMocker)response).getErrorLen());
    }

    @Test
    public void incorrectPasswordTest() throws IOException {
        patchParameter("existingPassword", "password2");
        patchParameter("newPassword", "newpass");
        patchParameter("newPasswordConfirm", "newpass");

        new PasswordModificationServlet().doPost(request, response);

        assertEquals(1, ((HttpServletResponseMocker)response).getErrorLen());
        assertThat(getError(0), containsString("INCORRECT PASSWORD"));
    }

    @Test
    public void passwordMismatched() throws IOException {
        patchParameter("existingPassword", "password");
        patchParameter("newPassword", "newpass");
        patchParameter("newPasswordConfirm", "newpass2");

        new PasswordModificationServlet().doPost(request, response);

        assertEquals(1, ((HttpServletResponseMocker)response).getErrorLen());
        assertThat(getError(0), containsString("MISMATCHED"));
    }

    @AfterEach
    public void tearDown(){
        ArrayList<FakeError> errors = ((HttpServletResponseMocker)response).getErrors();
        for(int i = 0; i < errors.size(); i++){
            System.out.println(errors.get(i).toString());
        }
    }
}
