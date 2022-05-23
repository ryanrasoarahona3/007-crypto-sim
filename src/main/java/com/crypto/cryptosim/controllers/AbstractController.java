package com.crypto.cryptosim.controllers;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.services.*;

import java.sql.SQLException;

public abstract class AbstractController {
    protected DatabaseManager dm;
    protected TickManager tm;
    protected MarketManager mm;
    protected UserDAO ur;
    protected ExchangeDAO er;
    protected TransactionManager trm;
    protected MessageDAO msr;
    protected SupportRequestDAO srd;

    protected WalletDAO wd;
    protected UserOperationDAO uod;
    protected WalletOperationDAO wod;
    protected OperationManager om;


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
