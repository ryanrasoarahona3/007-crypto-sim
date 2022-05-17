package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.ValuableCrypto;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.SessionManager;
import com.crypto.cryptosim.services.TransactionManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="cryptoTransactionServlet", value="/crypto-transaction")
public class CryptoTransactionServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        int cryptoId = Integer.valueOf(request.getParameter("crypto"));
        int cryptoN = Integer.valueOf(request.getParameter("n"));
        TransactionManager trm = TransactionManager.getInstance();

        try {
            DatabaseManager.getInstance().init(request.getServletContext());
            User u = SessionManager.getInstance().getActiveUser(request);
            ValuableCrypto c = MarketManager.getInstance().cryptoById(cryptoId);
            if(action.equals("buy"))
                trm.buyCoin(u, c, cryptoN);
            else if(action.equals("sell"))
                trm.sellCoin(u, c, cryptoN);
            else
                throw new Exception("Unknown action");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("wallet.jsp");
    }
}