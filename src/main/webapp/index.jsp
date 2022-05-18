<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %><%

    if(SessionManager.getInstance().isLoggedIn(request)){
        response.sendRedirect("./dashboard.jsp");
    }
%>

<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="login"/>
</jsp:include>
<div class="container">

    <div class="py-3 text-center">
        <img src="images/logo.PNG" width="350" class="d-inline-block align-top" alt=""/>
    </div>
    <p>
        Crypto-sim est une application de simulation de crypto-monnaie.
        Pour continuer à utiliser le site, veuillez
        <a href="login.jsp">vous connecter</a>. <br/>

        Si vous n'avez pas encore de compte, <a href="signup.jsp">inscrivez-vous</a>.
    </p>
    <p>
        Vous pouvez aussi choisir de <a href="installation.jsp">réinstaller</a> la base de donnnées
    </p>
</div>

<jsp:include page="inc/footer.jsp">
    <jsp:param name="page" value="login"/>
</jsp:include>