package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.controllers.InstallationController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name="installServlet", value="/install")
public class InstallationServlet extends HttpServlet {

    protected InstallationController ic;

    public void init(){
        ic = InstallationController.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ic.install();

        PrintWriter out = response.getWriter();
        out.write("Installation completed");
    }
}
