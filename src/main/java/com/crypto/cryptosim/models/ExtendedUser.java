package com.crypto.cryptosim.models;

public class ExtendedUser extends User{

    public boolean isAdmin(){
        if(getEmail().equals("admin@site.com"))
            return true;
        return false;
    }

}
