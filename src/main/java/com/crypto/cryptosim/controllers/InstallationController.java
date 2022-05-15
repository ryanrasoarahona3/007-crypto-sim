package com.crypto.cryptosim.controllers;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.ValuableCrypto;
import com.crypto.cryptosim.services.UserRepository;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

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

    /**
     *
     * @param context Pour les tests, ce paramètre peut prendre la valeur NULL
     */
    public void install(ServletContext context) throws SQLException {
        // Chargement des éléments de configuration
        if(context != null) {
            InputStream is = context.getResourceAsStream("/WEB-INF/db.properties");
            Properties p = new Properties();
            try {
                // Quelques valeurs par défaut
                p.load(is);
                DatabaseManager.setDriverName((String)p.get("DATABASE_DRIVER"));
                DatabaseManager.setUrl("jdbc:postgresql://"+p.get("DATABASE_HOST")+":"+p.get("DATABASE_PORT")+"/");
                DatabaseManager.setDbName((String)p.get("DATABASE_NAME"));
                DatabaseManager.setUserName((String)p.get("DATABASE_USER"));
                DatabaseManager.setPassword((String)p.get("DATABASE_PASSWORD"));
                System.out.println("OK");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Reset Tick Manager
        // TODO: Reset Tick Manager
        // Pour la résolution du bug lors de deux installations
        // à la suite
        // Ce bug met la valeur du Crypto Seed en -1
        // Initialisation des tables
        dm.init();
        mm.buildSQLTable();
        UserRepository.getInstance().buildSQLTable();

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

        TickManager.tearDown();
    }
}
