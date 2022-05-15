package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.controllers.InstallationController;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

@WebServlet(name="installationServlet", value="/installation")
public class InstallationServlet extends HttpServlet {

    protected InstallationController ic;

    public void init(){
        ic = InstallationController.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Ici, nous chargerons les configurations enregistrées dans db.properties.example
        PrintWriter out = response.getWriter();
        try {
            ic.install(getServletContext());
        } catch (SQLException e) {
            e.printStackTrace();
            e.printStackTrace(out);
            return;
        }
        response.sendRedirect("./?message=installed");
    }
}
