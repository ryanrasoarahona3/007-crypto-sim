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
                <table>
                    <tr>
                        <td>
                            <img style="float: left; width: 42px; margin-right: 1em;" src="<%=(c.getLogo()!=null)?c.getLogo():"https://socialistmodernism.com/wp-content/uploads/2017/07/placeholder-image.png?w=640"%>"/>
                        </td>
                        <td>
                            <strong><%= c.getName() %></strong>
                            <p>
                                <%= c.getValue() %> €
                            </p>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <%
        }
    %>
</div>