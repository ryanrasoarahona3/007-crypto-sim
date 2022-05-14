package com.crypto.cryptosim.controllers;

import com.crypto.cryptosim.MarketManager;
import com.crypto.cryptosim.PriceCurve;
import com.crypto.cryptosim.ValuableCrypto;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChartsController extends AbstractController{
    private static ChartsController instance = null;
    public static ChartsController getInstance(){
        if(instance == null){
            instance = new ChartsController();
        }
        return instance;
    }
    public static void tearDown(){
        ChartsController.instance = null;
    }
    public ChartsController(){
        super();
    }

    /**
     * Cette méthode sert uniquement d'exemple pour illustrer la page
     * @return
     */
    public String getExampleJSON(){
        // Initialisation des propriétés publics
        try {
            ValuableCrypto c = MarketManager.getInstance().cryptoByName("Bitcoin");
            return PriceCurve.getInstance().getLastMonthVariationJson(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO: Les deux dernières fonctions sont similaires, trouver un moyen de les factoriser
    // En utilisant deux accesseurs distinct dans un même contrôleur
    // Utiliser des Cas de tests unitaires pour développer le tout
    public String getExampleLabelJSON(){
        try {
            ValuableCrypto c = MarketManager.getInstance().cryptoByName("Bitcoin");
            ArrayList<Integer> series = PriceCurve.getInstance().getLastMonthVariation(c);
            ArrayList<String> l = new ArrayList<>();
            for(int i = 0; i < series.size(); i++)
                l.add("Date");
            return new Gson().toJson(l);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
