package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.SessionManager;
import com.crypto.cryptosim.services.UserDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PasswordModificationServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String existingPassword = request.getParameter("existingPassword");
        String newPassword = request.getParameter("newPassword");
        String newPasswordConfirm = request.getParameter("newPasswordConfirm");



        try {
            DatabaseManager.getInstance().init(request.getServletContext());
            User user = SessionManager.getInstance().getActiveUser(request);

            // Checking password
            if(existingPassword != user.getPassword())
                throw new Exception("Incorrect password");

            // Password confirmation
            if(newPassword != newPasswordConfirm)
                throw new Exception("Incorrect password confirmation");

            // Update it
            user.setPassword(newPassword);
            UserDAO.getInstance().updateDb(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
