package com.crypto.cryptosim.models;

import java.time.LocalDate;
import java.util.Date;

public class Operation {
    private int id;
    private int origin;
    private int destination;
    private int cryptoId;
    private int cryptoN;
    private int sum;
    private int exchangeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(int cryptoId) {
        this.cryptoId = cryptoId;
    }

    public int getCryptoN() {
        return cryptoN;
    }

    public void setCryptoN(int cryptoN) {
        this.cryptoN = cryptoN;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

}
