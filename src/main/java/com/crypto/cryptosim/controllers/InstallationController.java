package com.crypto.cryptosim.controllers;

import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.TickManager;
import com.crypto.cryptosim.ValuableCrypto;
import com.crypto.cryptosim.models.Exchange;
import com.crypto.cryptosim.models.Gender;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.ExchangeDAO;
import com.crypto.cryptosim.services.MessageDAO;
import com.crypto.cryptosim.services.TransactionManager;
import com.crypto.cryptosim.services.UserDAO;

import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

    private void install_exchange() throws SQLException {
        Exchange e1 = new Exchange(
                "https://s2.coinmarketcap.com/static/img/exchanges/64x64/270.png",
                "Binance",
                "http://binance.com",
                13565453,
                22132452,
                35231456,
                853
        );
        Exchange e2 = new Exchange(
                "https://s2.coinmarketcap.com/static/img/exchanges/64x64/89.png",
                "Coinbase Exchange",
                "http://coinbase.com",
                13456232,
                23456142,
                32456123,
                912
        );
        Exchange e3 = new Exchange(
                "https://s2.coinmarketcap.com/static/img/exchanges/64x64/524.png",
                "FTX",
                "http://ftx.com",
                12465321,
                19563216,
                33456321,
                755
        );
        Exchange e4 = new Exchange(
                "https://s2.coinmarketcap.com/static/img/exchanges/64x64/24.png",
                "Kraken",
                "http://kraken.com",
                94563211,
                15654412,
                26456321,
                802
        );
        Exchange e5 = new Exchange(
                "https://s2.coinmarketcap.com/static/img/exchanges/64x64/102.png",
                "Huobi Global",
                "http://huobi.com",
                8456336,
                2045632,
                29456321,
                605
        );
        Exchange e6 = new Exchange(
                "https://s2.coinmarketcap.com/static/img/exchanges/64x64/630.png",
                "Binance.US",
                "http://binance.us",
                82456321,
                1956543,
                25456321,
                590
        );
        Exchange e7 = new Exchange(
                "https://s2.coinmarketcap.com/static/img/exchanges/64x64/302.png",
                "Gate.io",
                "http://gate.io",
                74563212,
                15456321,
                22654142,
                555
        );

        er.add(e1);
        er.add(e2);
        er.add(e3);
        er.add(e4);
        er.add(e5);
        er.add(e6);
        er.add(e7);
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
        msr = MessageDAO.getInstance();

        trm.destroySQLTable();
        er.destroySQLTable();
        mm.destroySQLTable();
        msr.destroySQLTable();
        ur.destroySQLTable();

        ur.buildSQLTable();
        msr.buildSQLTable();
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

            install_exchange();

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

            john.setPseudo("");
            john.setPicture("");
            john.setBirth(Date.from(LocalDate.parse("1989-05-03").atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            john.setGender(Gender.MALE);
            john.setPhone("123456789");
            john.setAddress("1 Rue des érables");
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
