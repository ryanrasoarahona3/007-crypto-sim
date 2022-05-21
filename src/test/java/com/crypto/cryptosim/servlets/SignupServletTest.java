package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.ServletBaseTest;
import com.crypto.cryptosim.mockers.HttpServletResponseMocker;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.structures.Info;
import com.crypto.cryptosim.structures.InputError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SignupServletTest extends ServletBaseTest {

    private SignupServlet s;

    @BeforeEach
    public void init() throws SQLException {
        super.init();

        patchParameter("firstname", "John");
        patchParameter("lastname", "Doe");
        patchParameter("email", "john@gmail.com");
        patchParameter("password", "mypassword");
        patchParameter("passwordConfirm", "mypassword");

        s = new SignupServlet();
    }

    @Test
    public void successfulRegistrationTest() throws IOException, ServletException {

        s.doPost(request, response);

        assertEquals(0, s.getErrorLen());
        assertTrue(s.haveInfo(Info.SIGNUP_ACCOUNT_CREATED));
        assertThat(getRedirection(), containsString("login"));
    }

    @Test
    public void passwordMismatched() throws IOException, ServletException {
        patchParameter("passwordConfirm", "mipassword");

        s.doPost(request, response);

        assertTrue(s.haveInputError(InputError.SIGNUP_PASSWORD_MISMATCHED));
        assertThat(dispatcher.resource, containsString("signup"));
    }


}
