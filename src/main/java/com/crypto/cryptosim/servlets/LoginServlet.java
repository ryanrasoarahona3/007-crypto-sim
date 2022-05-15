package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name="loginServlet", value="/login")
public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User u = new User();
        u.setEmail(email);
        u.setPassword(password);

        try {
            DatabaseManager.getInstance().init(request.getServletContext());
            if(UserRepository.getInstance().verifyCredentials(u)){
                session.setAttribute("email", email);
                session.setAttribute("password", password);
                response.sendRedirect("dashboard.jsp?message=logged-in");
            }else{
                response.sendRedirect("login.jsp?errors=login-failed");
            }
        } catch (SQLException e) {
            e.printStackTrace(response.getWriter());
        }
    }
}
