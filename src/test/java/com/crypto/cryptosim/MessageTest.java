package com.crypto.cryptosim;

import com.crypto.cryptosim.models.Message;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.MessageDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class MessageTest extends AbstractTest{

    @Test
    public void messageSampleTest() throws SQLException {
        User u = new User();
        u.setEmail("John");
        ur.add(u);

        Message m = new Message();
        m.setRequest("request");
        m.setTitle("The title");
        m.setBody("The body");
        m.setSenderId(u.getId());
        MessageDAO.getInstance().add(m);

        ArrayList messages = MessageDAO.getInstance().getAll();
        assertEquals(1, messages.size());
    }
}
