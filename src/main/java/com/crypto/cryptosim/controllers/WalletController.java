package com.crypto.cryptosim.controllers;

import com.crypto.cryptosim.TickManager;

public class WalletController extends AbstractController {
    private static WalletController instance = null;
    public static WalletController getInstance(){
        if(instance == null){
            instance = new WalletController();
        }
        return instance;
    }
    public static void tearDown(){
        WalletController.instance = null;
    }



}
