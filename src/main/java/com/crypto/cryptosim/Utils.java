package com.crypto.cryptosim;

import java.util.ArrayList;

public class Utils {
    public static ArrayList<Object> reverseArray(ArrayList<?> arr){
        ArrayList<Object> output = new ArrayList<>();
        for(int i = arr.size()-1; i >= 0; i--)
            output.add(arr.get(i));
        return output;
    }
}
