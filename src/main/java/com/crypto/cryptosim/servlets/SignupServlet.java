package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.UserDAO;
import com.crypto.cryptosim.structures.Info;
import com.crypto.cryptosim.structures.InputError;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="signupServlet", value="/signup")
public class SignupServlet extends BaseServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Les entrées utilisateurs
        User u = new User();
        u.setFirstname(request.getParameter("firstname"));
        u.setLastname(request.getParameter("lastname"));
        u.setEmail(request.getParameter("email"));

        // Password mismatched
        if(request.getParameter("password") == request.getParameter("passwordConfirm"))
            u.setPassword(request.getParameter("password"));
        else
            addInputError(InputError.SIGNUP_PASSWORD_MISMATCHED);


        // IF there is no errors, then append it to the database
        if(getErrorLen() == 0){
            try {
                DatabaseManager.getInstance().init(request.getServletContext());
                UserDAO.getInstance().add(u);
                if(u.getId() == 0){
                    addInputError(InputError.SIGNUP_DATABASE_INSERTION_ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(getErrorLen() == 0) {
            addInfo(Info.SIGNUP_ACCOUNT_CREATED);
            response.sendRedirect("login.jsp");
        }else{
            request.setAttribute("errors", getErrors());
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }

        /*
        try {
            DatabaseManager.getInstance().init(request.getServletContext());
            UserDAO.getInstance().add(u);
        } catch (SQLException e) {
            e.printStackTrace(response.getWriter());
            return;
        }

        if(u.getId() != 0){
            response.sendRedirect("login.jsp?message=account-created");
        }else{
            response.sendRedirect("signup.jsp?errors=subscription-failed");
        }

         */
    }
}
