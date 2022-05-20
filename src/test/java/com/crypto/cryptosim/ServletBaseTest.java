package com.crypto.cryptosim;

import com.crypto.cryptosim.mockers.HttpServletResponseMocker;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class ServletBaseTest extends BaseTest{


    // Used as fake objects
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected ServletContext context;
    protected HttpSession session;

    @BeforeEach
    public void init() throws SQLException {
        super.init();

        // Used for Servlet testing
        // TODO: create ServletBaseTest (only for testing servlets)
        request = mock(HttpServletRequest.class);
        response = (HttpServletResponse) new HttpServletResponseMocker();
        context = mock(ServletContext.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(context);
    }

    protected String getError(int i){
        return ((HttpServletResponseMocker)response).getErrors().get(i).toString();
    }

    protected int getErrorLen(){
        return ((HttpServletResponseMocker)response).getErrorLen();
    }

    protected void patchParameter(String key, String value){
        when(request.getParameter(key)).thenReturn(value);
    }

    protected void patchSession(String key, String value){
        when(session.getAttribute(key)).thenReturn(value);
    }

    public String getRedirection(){
        return ((HttpServletResponseMocker)response).redirection;
    }
}
