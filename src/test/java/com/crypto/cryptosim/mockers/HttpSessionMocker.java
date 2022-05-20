package com.crypto.cryptosim.mockers;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionMocker {

    private Map<String, String> attributes;

    public String getAttribute(String key){
        // TODO: error management
        return attributes.get(key);
    }

    public void putAttribute(String key, String value){
        attributes.put(key, value);
    }

    public HttpSessionMocker(){
        attributes = new HashMap<>();
    }
}
