package com.crypto.cryptosim.controllers;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.services.UserRepository;

import java.sql.SQLException;

public abstract class AbstractController {
    protected DatabaseManager dm;
    protected TickManager tm;
    protected MarketManager mm;
    protected UserRepository ur;


    public AbstractController(){
        try {
            dm = DatabaseManager.getInstance();
            dm.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tm = TickManager.getInstance();
        mm = MarketManager.getInstance();
        ur = UserRepository.getInstance();
    }
}
