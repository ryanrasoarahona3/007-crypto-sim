package com.crypto.cryptosim;

import java.util.ArrayList;

public class Utils {
    public static ArrayList<Object> reverseArray(ArrayList<?> arr){
        ArrayList<Object> output = new ArrayList<>();
        for(int i = arr.size()-1; i >= 0; i--)
            output.add(arr.get(i));
        return output;
    }

    public static boolean isValidEmail(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
