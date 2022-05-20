package com.crypto.cryptosim.mockers;

import java.util.HashMap;
import java.util.Map;

public class HttpServletRequestMocker {

    private Map<String, String> parameters;

    private HttpSessionMocker session;

    public String getParameter(String key){
        // TODO: Simulate mock errors
        return parameters.get(key);
    }

    public void putParameter(String key, String value){
        parameters.put(key, value);
    }

    public HttpSessionMocker getSession(){
        return session;
    }

    public HttpServletRequestMocker(){
        // Initialize private properties
        parameters = new HashMap<>();
        session = new HttpSessionMocker();
    }
}
