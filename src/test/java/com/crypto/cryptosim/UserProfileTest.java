package com.crypto.cryptosim;

import com.crypto.cryptosim.models.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserProfileTest extends AbstractTest {
    @Test
    public void userModificationTest() throws Exception {

        User u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        User _u = ur.searchByEmail("john@gmail.com");
        _u.setFirstname("John");
        _u.setLastname("Wilson");
        ur.updateDb(_u);

        User __u = ur.searchByEmail("john@gmail.com");
        assertEquals("John", __u.getFirstname());

    }
}
