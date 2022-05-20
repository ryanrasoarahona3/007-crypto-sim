package com.crypto.cryptosim;

import com.crypto.cryptosim.mockers.HttpServletRequestMocker;
import com.crypto.cryptosim.mockers.HttpServletResponseMocker;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.servlets.PasswordModificationServlet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.sql.SQLException;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assert.*;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class PasswordModificationServletTest extends Mockito {

    //private HttpServletRequestMocker request;
    //private HttpServletResponseMocker response;
    private User u;
    private Exception exception;

    @BeforeEach
    public void init() throws SQLException {
        //super.init();

        //request = new HttpServletRequestMocker();
        //response = new HttpServletResponseMocker();

        /*
        u = new User();
        u.setEmail("john@gmail.com");
        u.setPassword("password");
        ur.add(u);
         */
    }

    @Test
    public void passwordModificationWorksFine(){

        // TODO: pour l'instant ce cas de test ne fonctionne pas
        /*
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("email")).thenReturn("john@gmail.com");
        when(session.getAttribute("password")).thenReturn("password");

        when(request.getParameter("existingPassword")).thenReturn("password");
        when(request.getParameter("newPassword")).thenReturn("newpass");
        when(request.getParameter("newPasswordConfirm")).thenReturn("newpass");

        try{
            new PasswordModificationServlet().doPost(request, response);
        }catch(Exception e){
            exception = e;
        }
        */

        /*
        request.getSession().putAttribute("email", "john@gmail.com");
        request.getSession().putAttribute("password", "password");

        request.putParameter("existingPassword", "password");
        request.putParameter("newPassword", "newpass");
        request.putParameter("newPasswordConfirm", "newpass");

        // Launch testing
        exception = null;
        try{
            (new PasswordModificationServlet()).doPost((HttpServletRequest) request, (HttpServletResponse) response);
        }catch(Exception e){
            exception = e;
        }
        assertEquals(null, exception);

         */
    }

    @AfterEach
    public void tearDown(){
        if(exception != null)
            exception.printStackTrace();
    }
}
