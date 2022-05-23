package com.crypto.cryptosim.servlets;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.User;
import com.crypto.cryptosim.services.SessionManager;
import com.crypto.cryptosim.structures.Info;
import com.crypto.cryptosim.structures.InputError;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseServlet extends HttpServlet {
    private ArrayList<InputError> inputErrors;
    private ArrayList<Info> immediateInfo;
    // TODO: appliquer cette factorisation à tous les classes de Servlet
    protected DatabaseManager dm;
    protected User activeUser;

    public void resetVars(){
        inputErrors = new ArrayList<>();
        immediateInfo = new ArrayList<>();
    }

    public void resetVars(HttpServletRequest request){
        resetVars();

        try {
            DatabaseManager.getInstance().init(request.getServletContext());
            activeUser = SessionManager.getInstance().getActiveUser(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    //
    protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String dest) throws IOException {
        request.getSession().setAttribute("errors", getErrors());
        request.getSession().setAttribute("infos", getInfos());
        response.sendRedirect(dest);
    }

    protected void dispatchForward(HttpServletRequest request, HttpServletResponse response, String resource) throws ServletException, IOException {
        request.getSession().setAttribute("errors", getErrors());
        request.getSession().setAttribute("infos", getInfos());
        request.getRequestDispatcher(resource).forward(request, response);
    }
}
