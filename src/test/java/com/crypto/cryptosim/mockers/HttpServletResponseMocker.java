package com.crypto.cryptosim.mockers;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import org.apache.catalina.connector.Response;

public class HttpServletResponseMocker extends Response{

    private ArrayList<FakeError> errors = new ArrayList<>();

    public String redirection = null;

    public void sendError(int code, String message){
        errors.add(new FakeError(code, message));
    }

    public ArrayList<FakeError> getErrors(){
        return errors;
    }

    public int getErrorLen(){
        return errors.size();
    }

    public void sendRedirect(String url){
        redirection = url;
    }
}
