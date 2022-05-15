package com.crypto.cryptosim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SemiRandomPriceManager {
    private static SemiRandomPriceManager instance = null;

    /**
     * Get Database connection
     * @return
     */
    protected Connection getConnection() throws SQLException {
        return DatabaseManager.getInstance().getConnection();
    }

    public static SemiRandomPriceManager getInstance(){
        if(instance == null){
            instance = new SemiRandomPriceManager();
        }
        return instance;
    }
    public static void tearDown(){
        SemiRandomPriceManager.instance = null;
    }

    public static ArrayList<ArrayList<Double>> tsvr(File test2) {
        ArrayList<ArrayList<Double>> Data = new ArrayList<>(); //initializing a new ArrayList out of String[]'s
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(test2))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                String[] lineItems = line.split(" "); //splitting the line and adding its items in String[]
                ArrayList<Double> outputLine = new ArrayList<>();
                for(int i = 0; i < lineItems.length; i++)
                    outputLine.add(Double.parseDouble(lineItems[i]));
                Data.add(outputLine);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return Data;
    }

    public SemiRandomPriceManager(){
        try{
            URL resource = getClass().getClassLoader().getResource("stocks-random.txt");
            File f = new File(resource.toURI());
            seedData = tsvr(f);

            syncUsedSeed();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    ArrayList<ArrayList<Double>> seedData;

    private ArrayList<Integer> usedSeeds = new ArrayList<>();

    /**
     * Synchroninser la valeur de usedSeed avec la base de donnée
     * Il s'agit de la liste des graines déjà utilisé
     */
    public void syncUsedSeed(){
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT crypto_seed from \"crypto\"");
            ArrayList<Integer> seeds = new ArrayList<>();
            while(rs.next())
                seeds.add(rs.getInt("crypto_seed"));
            usedSeeds = seeds;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int requestForSeed(){
        int randomNum;
        do{
            randomNum = ThreadLocalRandom.current().nextInt(0, seedData.size());
        }while(usedSeeds.contains(randomNum));
        syncUsedSeed();
        return randomNum;
    }

    public void updateCryptoPrice(ValuableCrypto c, int newCursor){
        int seed = c.getSeed();

        Random r = new Random();
        double randomNoise = 0.9 + (1.1 - 0.9) * r.nextDouble();

        Double newVal = (seedData.get(seed).get(newCursor) / seedData.get(seed).get(newCursor-1)) * c.getValue() * randomNoise;

        c.setValue((int) Math.round(newVal));
    }

    public void incrementCryptoPrice(ValuableCrypto c){

    }
}
