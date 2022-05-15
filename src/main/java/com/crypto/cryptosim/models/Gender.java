package com.crypto.cryptosim.models;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    UNKNOWN("UNKNOWN");

    public final String label;

    private Gender(String label){
        this.label = label;
    }

    public static Gender ofLabel(String label){
        if(label == "m")
            return Gender.MALE;
        if(label == "f")
            return Gender.FEMALE;
        if(label == "u")
            return Gender.UNKNOWN;
        return (Gender)null;
    }
}
