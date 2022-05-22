package com.crypto.cryptosim.servlets;


import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.SessionManager;
import com.crypto.cryptosim.services.UserDAO;
import com.crypto.cryptosim.structures.Info;
import com.crypto.cryptosim.structures.InputError;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="settingsServlet", value="/settings")
public class SettingsServlet extends BaseServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        resetVars();

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
        }

        if(user == null) {
            addInputError(InputError.SESSION_EXPIRED);
            sendRedirect(request, response, "login.jsp");
            return;
        }
        if(!user.getPassword().equals(existingPassword))
            addInputError(InputError.PASSUPDATE_INCORRECT_PASSWORD);

        if(newPassword.length() < 5)
            addInputError(InputError.PASSUPDATE_PASSWORD_TOO_SHORT);

        if(!newPassword.equals(newPasswordConfirm))
            addInputError(InputError.PASSUPDATE_PASSWORD_MISMATCHED);

        if(getErrorLen() > 0)
            sendRedirect(request, response, "settings.jsp");
        else {
            try {
                user.setPassword(newPassword);
                UserDAO.getInstance().updateDb(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            addInfo(Info.PASSUPDATE_SUCCESS);
            sendRedirect(request, response, "login.jsp");
        }
    }
}
