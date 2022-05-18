package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.Message;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.MessageDAO;
import com.crypto.cryptosim.services.SessionManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="supportServlet", value="/support")
public class SupportServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        try {
            DatabaseManager.getInstance().init(request.getServletContext());
            user = SessionManager.getInstance().getActiveUser(request);

            Message m = new Message();
            m.setRequest(request.getParameter("request"));
            m.setTitle(request.getParameter("title"));
            m.setBody(request.getParameter("body"));
            m.setSenderId(user.getId());
            MessageDAO.getInstance().add(m);

            response.sendRedirect("dashboard.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}