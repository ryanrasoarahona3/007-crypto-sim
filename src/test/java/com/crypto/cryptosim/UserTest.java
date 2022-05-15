package com.crypto.cryptosim;

import com.crypto.cryptosim.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest extends AbstractTest {

    @BeforeEach
    public void init() throws SQLException {
        super.init();
    }

    @Test
    public void insertionTest() throws SQLException {
        User u = new User();
        u.setEmail("user@mail.com");
        u.setPseudo("pseudo");
        u.setPassword("password");
        ur.add(u);

        assertNotEquals((Integer) null, u.getId());
    }

    @Test
    void verifyCredentials() throws SQLException {
        User u1 = new User();
        u1.setEmail("john@gmail.com");
        u1.setPassword("password");
        ur.add(u1);
        User u = new User();

        u.setEmail("john@gmail.com");
        u.setPassword("pass");
        assertEquals(false, ur.verifyCredentials(u));

        u.setEmail("john@gmail.co");
        u.setPassword("password");
        assertEquals(false, ur.verifyCredentials(u));

        u.setEmail("john@gmail.com");
        u.setPassword("password");
        assertEquals(true, ur.verifyCredentials(u));
    }
}
