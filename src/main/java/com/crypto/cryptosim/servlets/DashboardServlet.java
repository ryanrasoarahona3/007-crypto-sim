package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.SessionManager;
import com.crypto.cryptosim.structures.InputError;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name="dashboardServlet", value="/dashboard")
public class DashboardServlet extends BaseServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Check if user is connected
        User user = null;
        try {
            DatabaseManager.getInstance().init(request.getServletContext());
            user = SessionManager.getInstance().getActiveUser(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user == null){
            // TODO : normaliser les pages/servlets
            addInputError(InputError.SESSION_EXPIRED);

            // TODO: combiner les deux méthodes suivantes
            request.getSession().setAttribute("errors", getErrors());
            response.sendRedirect("login.jsp");
            return;
        }


        // Normal session
        request.getRequestDispatcher("dashboard.jsp").include(request, response);
    }
}
