package com.crypto.cryptosim.controllers;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.ValuableCrypto;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.ExchangeRepository;
import com.crypto.cryptosim.services.TransactionManager;
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

        dm.init(context);

        ur = UserRepository.getInstance();
        trm = TransactionManager.getInstance();
        er = ExchangeRepository.getInstance();
        mm = MarketManager.getInstance();

        trm.destroySQLTable();
        er.destroySQLTable();
        mm.destroySQLTable();
        ur.destroySQLTable();

        ur.buildSQLTable();
        mm.buildSQLTable();
        er.buildSQLTable();
        trm.buildSQLTable();

        // Créer Un crypto + 2 mois (60j) de données
        try {
            ValuableCrypto c1 = new ValuableCrypto();
            c1.setName("Bitcoin");
            c1.setSlug("BTC");
            c1.setValue(500);
            mm.add(c1);

            ValuableCrypto c2 = new ValuableCrypto();
            c2.setName("Euthérium");
            c2.setSlug("ETH");
            c2.setValue(100);
            mm.add(c2);

            ValuableCrypto c3 = new ValuableCrypto();
            c3.setName("Dodgecoin");
            c3.setSlug("DOG");
            c3.setValue(300);
            mm.add(c3);

            for(int i = 0; i < 60; i++)
                tm.nextTick();

            // Créer les utilisateurs par defaut

            User admin = new User();
            admin.setFirstname("Admin");
            admin.setEmail("admin@site.com");
            admin.setPassword("admin");
            ur.add(admin);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        TickManager.tearDown();
    }
}
