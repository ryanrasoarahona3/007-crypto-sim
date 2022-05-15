package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.SessionManager;
import com.crypto.cryptosim.services.TransactionManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="creditServlet", value="/credit")
public class CreditServlet extends HttpServlet {


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int sum = Integer.valueOf(request.getParameter("credit"));
            DatabaseManager.getInstance().init(request.getServletContext());
            User u = SessionManager.getInstance().getActiveUser(request);
            TransactionManager.getInstance().deposit(u, sum);
            response.sendRedirect("dashboard.jsp");
        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }

    }
}
