package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.services.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="loginServlet", value="/login")
public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            if(SessionManager.getInstance().login(request)){
                response.sendRedirect("dashboard.jsp?message=logged-in");
            }else{
                request.setAttribute("error", "CREDENTIAL_MISMATCHED");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                //response.sendRedirect("login.jsp?errors=login-failed");
            }
        } catch (SQLException e) {
            e.printStackTrace(response.getWriter());
        }


    }
}
