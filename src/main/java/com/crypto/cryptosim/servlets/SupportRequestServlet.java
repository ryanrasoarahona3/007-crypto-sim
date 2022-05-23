package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.models.SupportRequest;
import com.crypto.cryptosim.services.SupportRequestDAO;
import com.crypto.cryptosim.structures.Info;
import com.crypto.cryptosim.structures.InputError;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="supportRequestServlet", value="/supportRequest")
public class SupportRequestServlet extends BaseServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        resetVars(request);

        // For the view
        if(activeUser == null){
            addInputError(InputError.SESSION_EXPIRED);
            sendRedirect(request, response, "login.jsp");
            return;
        }

        // Treatment
        String title = request.getParameter("title");
        String message = request.getParameter("message");

        // TODO: null value exception

        try {
            SupportRequest s = new SupportRequest();
            s.setTitle(title);
            s.setMessage(message);
            s.setUserId(activeUser.getId());
            SupportRequestDAO.getInstance().add(s);
            addInfo(Info.SUPPORTREQUEST_SUCCESS);
            sendRedirect(request, response, "supportRequest");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        resetVars(request);

        // For the view
        if(activeUser == null){
            addInputError(InputError.SESSION_EXPIRED);
            sendRedirect(request, response, "login.jsp");
            return;
        }

        // Normal session
        request.getRequestDispatcher("supportRequest.jsp").forward(request, response);
    }
}
