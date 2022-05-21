package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.ServletBaseTest;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.structures.Info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SignupServletTest extends ServletBaseTest {


    @BeforeEach
    public void init() throws SQLException {
        super.init();
    }

    @Test
    public void successfulRegistrationTest() throws IOException {
        patchParameter("firstname", "John");
        patchParameter("lastname", "Doe");
        patchParameter("email", "john@gmail.com");
        patchParameter("password", "mypassword");
        patchParameter("passwordConfirm", "mypassword");

        SignupServlet s = new SignupServlet();
        s.doPost(request, response);

        assertEquals(0, s.getErrorLen());
        assertTrue(s.haveInfo(Info.SIGNUP_ACCOUNT_CREATED));
        assertThat(getRedirection(), containsString("login"));
    }
}
