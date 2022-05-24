package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.ServletBaseTest;
import com.crypto.cryptosim.models.ExtendedUser;
import com.crypto.cryptosim.models.SupportRequest;
import com.crypto.cryptosim.models.SupportResponse;
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

/**
 * Note that supportResponse is also included in this test Class
 */
public class SupportRequestServletTest extends ServletBaseTest {
    private SupportRequestServlet s;
    User u;
    User admin;

    @BeforeEach
    public void init() throws SQLException {
        super.init();
        u = new User();
        u.setEmail("john@gmail.com");
        u.setPassword("password");
        UserDAO.getInstance().add(u);

        admin = new User();
        admin.setEmail("admin@site.com");
        admin.setPassword("password");
        UserDAO.getInstance().add(admin);

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

    @Test
    public void successfulResponse() throws ServletException, IOException, SQLException {
        // TO connect as an administrator
        patchSession("email", u.getEmail());
        patchSession("password", u.getPassword());

        patchParameter("action", "request");
        patchParameter("title", "Please, help me");
        patchParameter("message", "This is the content of the message, the admin should reply to this");
        s.doPost(request, response);

        // Now the admin will reply
        patchSession("email", admin.getEmail());
        patchSession("password", admin.getPassword());

        patchParameter("action", "response");
        patchParameter("title", "Re-Please, help me");
        patchParameter("userId", ""+u.getId());
        patchParameter("message", "This is a response from the admin");
        s.doPost(request, response);

        assertEquals(0, s.getErrorLen());
        assertTrue(s.haveInfo(Info.SUPPORTRESPONSE_SUCCESS));

        ArrayList<SupportRequest> requests = SupportRequestDAO.getInstance().getAll();
        ArrayList<SupportResponse> responses = SupportRequestDAO.getInstance().getAll();
        assertEquals(1, responses.size());
        assertEquals(1, requests.size());
    }


}
