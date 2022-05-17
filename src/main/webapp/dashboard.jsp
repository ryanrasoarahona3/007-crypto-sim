<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page import="com.crypto.cryptosim.services.TransactionManager" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="com.crypto.cryptosim.DatabaseManager" %>

<%
    DatabaseManager.getInstance().init(request.getServletContext());
    User user = SessionManager.getInstance().getActiveUser(request);
%>

<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="dashboard"/>
</jsp:include>

<div class="row">
    <div class="col-md-3">
        <jsp:include page="inc/menu.jsp">
            <jsp:param name="page" value="dashboard"/>
        </jsp:include>
    </div>
    <div class="col-md-9">
        <h1>
            Bonjour <%= SessionManager.getInstance().getActiveUser(request).getFirstname() %>
        </h1>
        <div class="card">
            <div class="card-body">
                <h2>Votre solde est de <%= TransactionManager.getInstance().getBalance(user) %></h2>
                <form method="post" action="credit">
                    <h3>Créditer votre compte</h3>
                    <div class="mb-3">
                        <label for="credit" class="form-label">Montant à déposer</label>
                        <input type="number" class="form-control" id="credit" name="credit">
                    </div>
                    <div class="mb-3">
                        <input type="submit" class="btn btn-primary" value="Déposer">
                    </div>
                </form>
                <hr/>
                <form method="post" action="debit">
                    <h3>Débiter votre compte</h3>
                    <div class="mb-3">
                        <label for="debit" class="form-label">Montant à retirer</label>
                        <input type="number" class="form-control" id="debit" name="debit">
                    </div>
                    <div class="mb-3">
                        <input type="submit" class="btn btn-primary" value="Retirer">
                    </div>
                </form>
                <hr/>
            </div>
        </div>
        <p>
            The content
        </p>
    </div>
</div>

<jsp:include page="inc/footer.jsp">
    <jsp:param name="page" value="dashboard"/>
</jsp:include>