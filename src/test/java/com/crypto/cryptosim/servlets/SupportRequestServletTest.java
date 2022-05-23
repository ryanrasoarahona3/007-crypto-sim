package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.ServletBaseTest;
import com.crypto.cryptosim.models.SupportRequest;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.SupportRequestDAO;
import com.crypto.cryptosim.services.TransactionManager;
import com.crypto.cryptosim.services.UserDAO;
import com.crypto.cryptosim.structures.Info;
import com.crypto.cryptosim.structures.InputError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SupportRequestServletTest extends ServletBaseTest {
    private SupportRequestServlet s;
    User u;

    @BeforeEach
    public void init() throws SQLException {
        super.init();
        u = new User();
        u.setEmail("john@gmail.com");
        u.setPassword("password");
        UserDAO.getInstance().add(u);

        s = new SupportRequestServlet();
    }

    @Test
    public void successfulTest() throws SQLException, IOException, ServletException {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());

        patchParameter("title", "The title");
        patchParameter("message", "The message");
        s.doPost(request, response);
        assertEquals(0, s.getErrorLen());
        assertTrue(s.haveInfo(Info.SUPPORTREQUEST_SUCCESS));

        ArrayList<SupportRequest> srs = SupportRequestDAO.getInstance().getAll();
        assertEquals(1, srs.size());
    }

    @Test
    public void emptyTitleErrorTest() throws IOException, ServletException {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());

        patchParameter("title", "");
        patchParameter("message", "The message");
        s.doPost(request, response);
        assertEquals(1, s.getErrorLen());
        assertTrue(s.haveInputError(InputError.SUPPORTREQUEST_EMPTY_TITLE));
    }

    @Test
    public void emptyMessageErrorTest() throws IOException, ServletException {
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());

        patchParameter("title", "The title");
        patchParameter("message", "");
        s.doPost(request, response);
        assertEquals(1, s.getErrorLen());
        assertTrue(s.haveInputError(InputError.SUPPORTREQUEST_EMPTY_MESSAGE));
    }


}
