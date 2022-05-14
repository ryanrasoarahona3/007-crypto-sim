package com.crypto.cryptosim;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager instance = null;

    // These variables should be configured inside environement file
    private static String url = "jdbc:postgresql://localhost:5433/";
    private static String dbName =  "crypto-deploy";
    private static String urlParams = "?useUnicode=yes&characterEncoding=utf8";
    private static String driverName = "org.postgresql.Driver";
    private static String userName = "astrozeneka";
    private static String password = "deltaCharlie52";

    // TODO: IMPORTER CES FONCTIONS DANS UN FICHIER SÉPARÉ

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DatabaseManager.url = url;
    }

    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        DatabaseManager.dbName = dbName;
    }

    public static String getUrlParams() {
        return urlParams;
    }

    public static void setUrlParams(String urlParams) {
        DatabaseManager.urlParams = urlParams;
    }

    public static String getDriverName() {
        return driverName;
    }

    public static void setDriverName(String driverName) {
        DatabaseManager.driverName = driverName;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        DatabaseManager.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DatabaseManager.password = password;
    }


    // TODO: ––––––––––––––

    private Connection con;

    public DatabaseManager(){
        // La méthode init() doit être appelé manuellement après la configuration de la base de donnée
    }

    public void init() throws SQLException {
        try{
            // Check database connection
            Class.forName(driverName).newInstance();
            con = DriverManager.getConnection(url+dbName+urlParams, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return con;
    }

    public static DatabaseManager getInstance(){
        if(instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }
}

