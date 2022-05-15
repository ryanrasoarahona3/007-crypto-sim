package com.crypto.cryptosim;

import com.crypto.cryptosim.services.ExchangeRepository;
import com.crypto.cryptosim.services.TransactionRepository;
import com.crypto.cryptosim.services.UserRepository;

import java.sql.SQLException;

public abstract class AbstractTest {
    protected DatabaseManager dm;
    protected TickManager tm;
    protected MarketManager mm;
    protected UserRepository ur;
    protected ExchangeRepository er;
    protected TransactionRepository tr;

    protected void init() throws SQLException {
        dm = DatabaseManager.getInstance();
        dm.setDbName("crypto-test");
        dm.init();
        tm = TickManager.getInstance();
        mm = MarketManager.getInstance();
        mm.buildSQLTable();
        ur = UserRepository.getInstance();
        ur.buildSQLTable();
        er = ExchangeRepository.getInstance();
        er.buildSQLTable();
        tr = TransactionRepository.getInstance();
        tr.buildSQLTable();
    }
}
