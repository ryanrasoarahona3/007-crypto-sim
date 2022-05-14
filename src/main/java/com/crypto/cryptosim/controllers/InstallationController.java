package com.crypto.cryptosim.controllers;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.ValuableCrypto;

import java.sql.SQLException;

public class InstallationController extends AbstractController{
    private static InstallationController instance = null;
    public static InstallationController getInstance(){
        if(instance == null){
            instance = new InstallationController();
        }
        return instance;
    }
    public InstallationController(){
        super();
    }

    public void install(){
        // Reset Tick Manager
        // TODO: Reset Tick Manager
        // Pour la résolution du bug lors de deux installations
        // à la suite
        // Ce bug met la valeur du Crypto Seed en -1
        // Initialisation des tables
        try {
            dm.init();
            mm.buildSQLTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Créer Un crypto + 2 mois (60j) de données
        try {
            ValuableCrypto c = new ValuableCrypto();
            c.setName("Bitcoin");
            c.setSlug("BTC");
            c.setValue(500);
            mm.add(c);

            for(int i = 0; i < 60; i++)
                tm.nextTick();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
