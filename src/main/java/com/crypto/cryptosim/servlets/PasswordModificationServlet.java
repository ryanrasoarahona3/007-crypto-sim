package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.SessionManager;
import com.crypto.cryptosim.services.UserDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PasswordModificationServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String existingPassword = request.getParameter("existingPassword");
        String newPassword = request.getParameter("newPassword");
        String newPasswordConfirm = request.getParameter("newPasswordConfirm");



        User user = null;
        try {
            DatabaseManager.getInstance().init(request.getServletContext());
            user = SessionManager.getInstance().getActiveUser(request);
        }catch (SQLException e) {
            e.printStackTrace();
            return;
        }catch (Exception e) {
            response.sendError(412, "FAILED TO GET CONTEXT");
        }
        // Checking password
        if(!existingPassword.equals(user.getPassword()))
            response.sendError(412, "INCORRECT PASSWORD");

        // Password confirmation
        if(newPassword != newPasswordConfirm)
            response.sendError(412, "PASSWORD MISMATCHED");

        // Update it
        user.setPassword(newPassword);
        try {
            UserDAO.getInstance().updateDb(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
