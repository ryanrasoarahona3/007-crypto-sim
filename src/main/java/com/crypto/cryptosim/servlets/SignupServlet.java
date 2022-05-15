package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="signupServlet", value="/signup")
public class SignupServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        // Les entrées utilisateurs
        User u = new User();
        u.setFirstname(request.getParameter("firstname"));
        u.setLastname(request.getParameter("lastname"));
        u.setEmail(request.getParameter("email"));
        u.setPassword(request.getParameter("password"));
        try {
            DatabaseManager.getInstance().init(request.getServletContext());
            UserRepository.getInstance().add(u);
        } catch (SQLException e) {
            e.printStackTrace(response.getWriter());
            return;
        }

        if(u.getId() != 0){
            response.sendRedirect("login.jsp?message=account-created");
        }else{
            response.sendRedirect("signup.jsp?errors=subscription-failed");
        }
    }
}
