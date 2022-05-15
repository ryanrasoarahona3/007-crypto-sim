<%@ page import="com.crypto.cryptosim.services.SessionManager" %><%
    SessionManager.getInstance().logOut(request);
    response.sendRedirect("./");
%>