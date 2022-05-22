package com.crypto.cryptosim.services;

import com.crypto.cryptosim.DatabaseManager;
import com.crypto.cryptosim.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class SessionManager {
    private static SessionManager instance = null;
    public static SessionManager getInstance(){
        if(instance == null)
            instance = new SessionManager();
        return instance;
    }
    public static void tearDown(){
        SessionManager.instance = null;
    }

    private User activeUser = null;

    public boolean login(HttpServletRequest request) throws SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User u = new User();
        u.setEmail(email);
        u.setPassword(password);

        HttpSession session = request.getSession();
        DatabaseManager.getInstance().init(request.getServletContext());
        if(UserDAO.getInstance().verifyCredentials(u)){
            session.setAttribute("email", email);
            session.setAttribute("password", password);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Méthode à appeler obligatoirement dans chaque page JSP/Servlet
     * @param request
     * @return
     * @throws SQLException
     */
    public boolean isLoggedIn(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String password = (String) session.getAttribute("password");
        if(email == "" || password == "") return false;

        User u = new User();
        u.setEmail(email);
        u.setPassword(password);

        DatabaseManager.getInstance().init(request.getServletContext());
        if(UserDAO.getInstance().verifyCredentials(u)){
            session.setAttribute("email", email);
            session.setAttribute("password", password);
            return true;
        }else{
            return false;
        }
    }

    public void logOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }

    public User getActiveUser(HttpServletRequest request) throws SQLException {
        if(activeUser != null)
            return activeUser;
        else{
            HttpSession session = request.getSession();
            DatabaseManager.getInstance().init(request.getServletContext());
            User u = UserDAO.getInstance().searchByEmail((String) session.getAttribute("email"));
            return u;
        }
    }
}
