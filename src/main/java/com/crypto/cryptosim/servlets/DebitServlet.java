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

/**
 * @deprecated Now dashboardservlet is used
 */
@WebServlet(name="debitServlet", value="/debit")
public class DebitServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Normalement, on devrait checker si la transaction est possible
            int sum = Integer.valueOf(request.getParameter("debit"));
            DatabaseManager.getInstance().init(request.getServletContext());
            User u = SessionManager.getInstance().getActiveUser(request);
            TransactionManager.getInstance().withdrawal(u, sum);
            response.sendRedirect("dashboard.jsp");
        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }

    }
}
