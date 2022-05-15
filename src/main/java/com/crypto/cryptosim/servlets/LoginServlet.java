package com.crypto.cryptosim.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="loginServlet", value="/login")
public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        //PrintWriter out = response.getWriter();
        //out.print("Previously entered email :" + session.getAttribute("email"));
        String email = request.getParameter("email");

        session.setAttribute("email", email);
        request.getRequestDispatcher("login.jsp").include(request, response);
    }
}
