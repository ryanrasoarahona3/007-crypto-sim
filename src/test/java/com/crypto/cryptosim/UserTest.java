package com.crypto.cryptosim;

import com.crypto.cryptosim.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
}
