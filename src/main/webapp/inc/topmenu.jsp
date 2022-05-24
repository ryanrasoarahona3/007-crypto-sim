<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light mb-5">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">
            <img src="images/logo.PNG" height="50" class="d-inline-block align-top" alt="">

        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <%
                        DatabaseManager.getInstance().init(request.getServletContext());
                        User user = SessionManager.getInstance().getActiveUser(request);
                        if(user != null){
                    %>
                    <a class="nav-link" href="#">Hello <%= user.getFirstname() %></a>
                    <%
                        }
                    %>
                    <!--
                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                    -->
                </li>
            </ul>
        </div>
    </div>
</nav>