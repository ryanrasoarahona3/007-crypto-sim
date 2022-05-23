package com.crypto.cryptosim.models;

import java.time.LocalDate;
import java.util.Date;

public class Wallet {
    private int id;
    private String name;
    private int userId;
    private int cryptoId;
    private LocalDate date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(int cryptoId) {
        this.cryptoId = cryptoId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
