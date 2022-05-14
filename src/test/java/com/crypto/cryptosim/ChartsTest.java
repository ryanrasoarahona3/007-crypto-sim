package com.crypto.cryptosim;

import com.crypto.cryptosim.controllers.ChartsController;
import com.crypto.cryptosim.controllers.InstallationController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ChartsTest extends AbstractTest{
    private InstallationController ic;
    private ChartsController cc;

    @BeforeEach
    public void init() throws SQLException {
        super.init();
        ic = InstallationController.getInstance();
        cc = ChartsController.getInstance();
        ic.install();

    }

    @Test
    public void jsonDataTest(){
        String chart_json = cc.getExampleJSON();
        assertNotEquals(null, chart_json);
    }

    @AfterEach
    void tearDown(){

    }
}
