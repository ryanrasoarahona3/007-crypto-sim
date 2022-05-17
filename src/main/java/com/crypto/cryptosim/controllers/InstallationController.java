package com.crypto.cryptosim.controllers;

import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.ValuableCrypto;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.ExchangeDAO;
import com.crypto.cryptosim.services.TransactionManager;
import com.crypto.cryptosim.services.UserDAO;

import javax.servlet.ServletContext;
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

    /**
     *
     * @param context Pour les tests, ce paramètre peut prendre la valeur NULL
     */
    public void install(ServletContext context) throws SQLException {

        dm.init(context);

        ur = UserDAO.getInstance();
        trm = TransactionManager.getInstance();
        er = ExchangeDAO.getInstance();
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

            ValuableCrypto c4 = new ValuableCrypto();
            c4.setName("Ripple");
            c4.setSlug("XRP");
            c4.setValue(530);
            mm.add(c4);

            ValuableCrypto c5 = new ValuableCrypto();
            c5.setName("Cardano");
            c5.setSlug("ADA");
            c5.setValue(320);
            mm.add(c5);

            ValuableCrypto c6 = new ValuableCrypto();
            c6.setName("Binance Coin");
            c6.setSlug("BNB");
            c6.setValue(520);
            mm.add(c6);

            for(int i = 0; i < 60; i++)
                tm.nextTick();

            // Créer les utilisateurs par defaut

            User admin = new User();
            admin.setFirstname("Admin");
            admin.setEmail("admin@site.com");
            admin.setPassword("admin");
            ur.add(admin);

            User john = new User();
            john.setFirstname("John");
            john.setLastname("Wilson");
            john.setEmail("john@site.com");
            john.setPassword("john");
            ur.add(john);

            trm.deposit(john, 10000);
            trm.buyCoin(john, c1, 5);

            for(int i = 0; i < 2; i++) tm.nextTick();

            trm.deposit(admin, 2000);
            trm.buyCoin(john, c2, 3);
            trm.buyCoin(john, c3, 3);
            trm.buyCoin(john, c5, 1);

            for(int i = 0; i < 3; i++) tm.nextTick();

            trm.withdrawal(admin, 1000);
            trm.sellCoin(john, c3, 1);
            trm.withdrawal(john, 200);

            for(int i = 0; i < 1; i++) tm.nextTick();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        TickManager.tearDown();
    }
}
