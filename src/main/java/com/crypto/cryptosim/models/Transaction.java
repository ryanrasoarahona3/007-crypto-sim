package com.crypto.cryptosim.models;

public class Transaction {
    private int id;
    private int transmitterId;
    private int recipientId;
    private int cryptoId;
    private int cryptoN; // number of crypto
    private int sum;
    private int exchangeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransmitterId() {
        return transmitterId;
    }

    public void setTransmitterId(int transmitterId) {
        this.transmitterId = transmitterId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
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
