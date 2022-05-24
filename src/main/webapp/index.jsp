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
        Crypto-sim is a web-based application used for cryptocurrency simulation.
        To use the simulator, please
        <a href="login.jsp">log in</a>. <br/>

        If this is your first visit to the site, you can <a href="signup.jsp">create a new account</a>.
    </p>
    <p>
        You can also choose to <a href="installation.jsp">reinstall</a> the database.
    </p>
</div>

<jsp:include page="inc/footer.jsp">
    <jsp:param name="page" value="login"/>
</jsp:include>