package com.crypto.cryptosim;

import com.crypto.cryptosim.controllers.ChartsController;
import com.crypto.cryptosim.controllers.InstallationController;
import com.crypto.cryptosim.structures.ChartData;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ChartsTest extends AbstractTest{
    private ArrayList<Integer> lastWeekPrices;
    private ArrayList<String> lastWeekDates;
    private ArrayList<Integer> last3MonthsPrices;
    private ArrayList<String> last3MonthsDates;
    ValuableCrypto c;

    @BeforeEach
    public void install() throws SQLException {
        lastWeekPrices = new ArrayList<>();
        last3MonthsPrices = new ArrayList<>();
        lastWeekDates = new ArrayList<>();
        last3MonthsDates = new ArrayList<>();

        c = new ValuableCrypto();
        c.setName("Bitcoin");
        c.setValue(500);
        mm.add(c);

        int N = 150;
        for(int i = 0; i < N; i++) {
            tm.nextTick();
            if(i+7 >= N) {
                lastWeekPrices.add(c.getValue());
                // TODO : à Franciser
                lastWeekDates.add(tm.getDate().toString());
            }
            if(i+90 >= N) {
                last3MonthsPrices.add(c.getValue());
                // TODO : à Franciser
                last3MonthsDates.add(tm.getDate().toString());
            }
        }
    }

    @Test
    public void jsonDataTest(){
        String chart_json = cc.getExampleJSON();
        assertNotEquals(null, chart_json);
    }

    @Test
    public void lastWeekVariation() throws SQLException {
        ChartData last7 = PriceCurve.getInstance().getLastNDaysVariation(c, 7);
        assertEquals(lastWeekPrices, last7.data);
        assertEquals(lastWeekDates, last7.labels);
        assertEquals(7, lastWeekPrices.size());
    }


    @Test
    public void last3MonthVariation() throws SQLException {
        ChartData last90 = PriceCurve.getInstance().getLastNDaysVariation(c, 90);
        assertEquals(last3MonthsPrices, last90.data);
        assertEquals(last3MonthsDates, last90.labels);
        assertEquals(90, last90.data.size());
    }

    @Test
    public void testPricePosition() throws SQLException {
        ChartData last90 = PriceCurve.getInstance().getLastNDaysVariation(c, 90);
        assertEquals(c.getValue(), last90.data.get(89));
    }
}
