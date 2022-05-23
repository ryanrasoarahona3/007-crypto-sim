package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.Crypto;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.ValuableCrypto;
import com.crypto.cryptosim.models.Wallet;
import com.crypto.cryptosim.services.OperationManager;
import com.crypto.cryptosim.services.WalletDAO;
import com.crypto.cryptosim.structures.Info;
import com.crypto.cryptosim.structures.InputError;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="walletServlet", value="/wallet")
public class WalletServlet extends BaseServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /**
         * 1. Création wallet
         * 2. Déposer du liquide
         * 3. Retirer du liquide
         * 4. Achat crypto
         * 5. Vente Crypto
         */
        resetVars(request);
        String action = request.getParameter("action");

        if(activeUser == null) {
            addInputError(InputError.SESSION_EXPIRED);
            sendRedirect(request, response, "login.jsp");
            return;
        }

        if(action.equals("create_wallet")){
            String walletName = request.getParameter("wallet_name");
            int cryptoId = Integer.parseInt(request.getParameter("wallet_crypto"));
            int userId = activeUser.getId();

            if(walletName.equals(""))
                addInputError(InputError.WALLET_EMPTY_NAME);
            if(getErrorLen() > 0){
                dispatchForward(request, response, "wallet.jsp");
                return;
            }

            try {
                Wallet w = new Wallet();
                w.setName(walletName);
                w.setUserId(userId);
                w.setCryptoId(cryptoId);
                WalletDAO.getInstance().add(w);

                addInfo(Info.WALLET_CREATED);
                sendRedirect(request, response, "wallet.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (action.equals("buy_crypto")) {
            int walletId = Integer.parseInt(request.getParameter("wallet"));
            int n = Integer.parseInt(request.getParameter("n"));

            try {
                Wallet wallet = WalletDAO.getInstance().getById(walletId);
                ValuableCrypto crypto = MarketManager.getInstance().cryptoById(wallet.getCryptoId());

                int totalPrice = crypto.getValue() * n;
                int balance = OperationManager.getInstance().getBalance(activeUser);
                if(totalPrice > balance){
                    addInputError(InputError.WALLET_NOT_ENOUGH_BALANCE);
                    dispatchForward(request, response, "wallet.jsp");
                    return;
                }

                OperationManager.getInstance().buyCrypto(wallet, n);

                addInfo(Info.WALLET_TRANSACTION_DONE);
                sendRedirect(request, response, "wallet.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        resetVars(request);

        if(activeUser == null) {
            addInputError(InputError.SESSION_EXPIRED);
            sendRedirect(request, response, "login.jsp");
            return;
        }

        dispatchForward(request, response, "wallet.jsp");
    }
}
