package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.Utils;
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

        // Firstname
        if(!request.getParameter("firstname").equals(""))
            u.setFirstname(request.getParameter("firstname"));
        else
            addInputError(InputError.SIGNUP_FIRSTNAME_REQUIRED);

        // Lastname
        if(!request.getParameter("lastname").equals(""))
            u.setLastname(request.getParameter("lastname"));
        else
            addInputError(InputError.SIGNUP_LASTNAME_REQUIRED);

        // Email
        if(Utils.isValidEmail(request.getParameter("email")))
            u.setEmail(request.getParameter("email"));
        else
            addInputError(InputError.SIGNUP_EMAIL_INVALID);

        // Password length
        if(request.getParameter("password").length() < 5)
            addInputError(InputError.SIGNUP_PASSWORD_TOO_SHORT);

        // Password mismatched
        if(request.getParameter("password").equals(request.getParameter("passwordConfirm")))
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
            // TODO : redirect parameters to attribute for error display
            request.setAttribute("firstname", request.getParameter("firstname"));
            request.setAttribute("lastname", request.getParameter("lastname"));
            request.setAttribute("email", request.getParameter("email"));

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
