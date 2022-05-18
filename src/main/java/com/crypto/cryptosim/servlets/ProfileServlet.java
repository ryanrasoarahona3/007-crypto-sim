package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.Gender;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.SessionManager;
import com.crypto.cryptosim.services.UserDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@WebServlet(name="profileServlet", value="/profile")
public class ProfileServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            DatabaseManager.getInstance().init(request.getServletContext());
            User u = SessionManager.getInstance().getActiveUser(request);

            u.setEmail(request.getParameter("email"));
            // u.setPseudo(request.getParameter("pseudo"));
            // u.setPassword(request.getParameter("password"));
            u.setFirstname(request.getParameter("firstname"));
            u.setLastname(request.getParameter("lastname"));
            u.setPicture(request.getParameter("picture"));
            u.setBirth(
                    Date.from(LocalDate.parse(request.getParameter("birth"), DateTimeFormatter.ofPattern("MM/dd/yyyy")).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
            );
            u.setGender(Gender.ofLabel(request.getParameter("gender")));
            u.setPhone(request.getParameter("phone"));
            u.setAddress(request.getParameter("address"));
            UserDAO.getInstance().updateDb(u);

            response.sendRedirect("profile.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
