package com.crypto.cryptosim;

import com.crypto.cryptosim.controllers.ChartsController;
import com.crypto.cryptosim.services.ExchangeDAO;
import com.crypto.cryptosim.services.MessageDAO;
import com.crypto.cryptosim.services.TransactionManager;
import com.crypto.cryptosim.services.UserDAO;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

public abstract class AbstractTest {
    protected DatabaseManager dm;
    protected TickManager tm;
    protected MarketManager mm;
    protected UserDAO ur;
    protected ExchangeDAO er;
    protected TransactionManager trm;
    protected ChartsController cc;
    protected MessageDAO msr;

    @BeforeEach
    protected void init() throws SQLException {
        dm = DatabaseManager.getInstance();
        dm.setDbName("crypto-test");
        dm.init();


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
        mm.buildSQLTable();
        er.buildSQLTable();
        trm.buildSQLTable();
        msr.buildSQLTable();

        tm = TickManager.getInstance();

    }
}
