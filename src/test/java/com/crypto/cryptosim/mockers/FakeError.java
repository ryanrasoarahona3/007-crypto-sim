package com.crypto.cryptosim.mockers;

public class FakeError {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public FakeError(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString(){
        return "" + code + " ERROR – " + message;
    }
}
