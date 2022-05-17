package com.crypto.cryptosim.controllers;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.services.ExchangeDAO;
import com.crypto.cryptosim.services.TransactionManager;
import com.crypto.cryptosim.services.UserDAO;

import java.sql.SQLException;

public abstract class AbstractController {
    protected DatabaseManager dm;
    protected TickManager tm;
    protected MarketManager mm;
    protected UserDAO ur;
    protected ExchangeDAO er;
    protected TransactionManager trm;


    public AbstractController(){
        try {
            dm = DatabaseManager.getInstance();
            dm.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tm = TickManager.getInstance();
        mm = MarketManager.getInstance();
        ur = UserDAO.getInstance();
    }
}
