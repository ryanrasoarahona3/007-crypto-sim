package com.crypto.cryptosim;

import com.crypto.cryptosim.models.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TickManagerTest extends AbstractTest{

    @Test
    public void getDate() throws SQLException {
        User u = new User();
        u.setEmail("john@gmail.com");
        ur.add(u);

        tm.nextTick();
        tm.nextTick();
        LocalDate date = tm.getDate();
        assertEquals(3, date.getDayOfMonth());
    }
}
