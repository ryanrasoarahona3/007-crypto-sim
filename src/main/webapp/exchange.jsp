<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.crypto.cryptosim.models.ReferencedMessage" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.crypto.cryptosim.services.MessageDAO" %>
<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="com.crypto.cryptosim.models.ExtendedUser" %>
<%@ page import="com.crypto.cryptosim.models.Exchange" %>
<%@ page import="com.crypto.cryptosim.services.ExchangeDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="exchange"/>
</jsp:include>

<div class="row">
    <div class="col-md-3">
        <jsp:include page="inc/menu.jsp">
            <jsp:param name="page" value="wallet"/>
        </jsp:include>
    </div>
    <div class="col-md-9">

        <div class="my-5">
            <div class="row">
                <div class="col-md-6">
                    <h2>Exchanges</h2>
                </div>
                <div class="col-md-6">
                    <p>
                        <!-- TODO: Pie Chart -->
                    </p>
                </div>
            </div>
        </div>

        <div class="my-5">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Site</th>
                    <th scope="col">Volume en 24h</th>
                    <th scope="col">Volume en 7j</th>
                    <th scope="col">Volume en 30j</th>
                    <th scope="col">Liquidité</th>
                </tr>
                </thead>

                <tbody>
                <%
                    DatabaseManager.getInstance().init(request.getServletContext());
                    ArrayList<Exchange> exchanges = ExchangeDAO.getInstance().getAll();
                    for(int i = 0; i < exchanges.size(); i++){
                        Exchange e = exchanges.get(i);
                %>
                <tr>
                    <th scope="row"><%= e.getId() %></th>
                    <td><a href="<%= e.getUrl()%>"><img width="32" class="mx-2" src="<%= e.getLogo()%>"/> <%= e.getName() %> </a></td>
                    <td><%= e.getVolDay() %> €</td>
                    <td><%= e.getVolWeek() %> €</td>
                    <td><%= e.getVolMonth() %> €</td>
                    <td><%= e.getVolLiquidity()%></td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>

    </div>
</div>




<jsp:include page="inc/footer.jsp">
    <jsp:param name="page" value="exchange"/>
</jsp:include>