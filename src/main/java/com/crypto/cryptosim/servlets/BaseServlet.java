package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.structures.Info;
import com.crypto.cryptosim.structures.InputError;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseServlet extends HttpServlet {
    private ArrayList<InputError> inputErrors;
    private ArrayList<Info> immediateInfo;

    protected void addInputError(InputError e){
        inputErrors.add(e);
    }

    protected boolean haveInputError(InputError e){
        return inputErrors.contains(e);
    }

    public int getErrorLen(){
        return inputErrors.size();
    }

    public ArrayList<InputError> getErrors(){
        return inputErrors;
    }

    protected void addInfo(Info i){
        immediateInfo.add(i);
    }

    protected boolean haveInfo(Info i){
        return immediateInfo.contains(i);
    }

    protected int getInfoLen(){
        return immediateInfo.size();
    }

    protected ArrayList<Info> getInfos(){
        return immediateInfo;
    }

    public BaseServlet(){
        inputErrors = new ArrayList<>();
        immediateInfo = new ArrayList<>();
    }
}
