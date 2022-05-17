<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.crypto.cryptosim.ValuableCrypto" %>
<%@ page import="com.crypto.cryptosim.MarketManager" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <%
        ArrayList<ValuableCrypto> cryptos = MarketManager.getInstance().getAll();
        for(int i = 0; i < cryptos.size(); i++){
            ValuableCrypto c = cryptos.get(i);
    %>
    <div class="col-md-3 my-3">
        <div class="card">
            <div class="card-body">
                <h3><%= c.getName() %></h3>
                <p>
                    <%= c.getValue() %> €
                </p>
            </div>
        </div>
    </div>
    <%
        }
    %>
</div>