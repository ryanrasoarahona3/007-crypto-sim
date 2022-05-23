<%@ page import="com.crypto.cryptosim.structures.InputError" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.crypto.cryptosim.structures.Info" %>
<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="com.crypto.cryptosim.services.TransactionManager" %>
<%@ page import="com.crypto.cryptosim.MarketManager" %>
<%@ page import="com.crypto.cryptosim.ValuableCrypto" %>
<%
    DatabaseManager.getInstance().init(request.getServletContext());
    User u = SessionManager.getInstance().getActiveUser(request);
    TransactionManager trm = TransactionManager.getInstance();
    ArrayList<ValuableCrypto> cryptos = MarketManager.getInstance().getAll();
%>
<%
    ArrayList<InputError> errors = (ArrayList<InputError>) request.getSession().getAttribute("errors");
    ArrayList<Info> infos = (ArrayList<Info>) request.getSession().getAttribute("infos");
    if(errors == null) errors = new ArrayList<>();
    if(infos == null) infos = new ArrayList<>();
    request.getSession().removeAttribute("errors");
    request.getSession().removeAttribute("infos");
%>

<div class="card">
    <div class="card-body">
        <h4>Add new wallet</h4>
        <form method="post" action="wallet">
            <input type="hidden" name="action" value="create_wallet"/>

            <% if(infos.contains(Info.WALLET_CREATED)){ %>
            <div class="my-3 text-success">
                <p>
                    Your wallet has been successfully created
                </p>
            </div>
            <% } %>


            <div class="mb-3">
                <label for="wallet_name" class="form-label">Name</label>
                <input type="text" class="form-control" id="wallet_name" name="wallet_name"
                       value="<%= (request.getParameter("wallet_name")!=null)?request.getParameter("wallet_name"):""%>"
                >
                <% if(errors.contains(InputError.WALLET_EMPTY_NAME)) { %>
                <p class="text-danger" style="font-size: .8em;">
                    Required field
                </p>
                <% } %>
            </div>

            <div class="mb-3">
                <label for="wallet_crypto" class="form-label">Which crypto do you want to use ?</label>
                <select class="form-select col-md-4" id="wallet_crypto" name="wallet_crypto">
                    <%
                        for(int i = 0; i < cryptos.size(); i++){
                    %>
                    <option value="<%= cryptos.get(i).getId() %>"><%= cryptos.get(i).getSlug() %></option>
                    <%
                        }
                    %>
                </select>
            </div>
            <input type="submit" class="btn btn-primary" value="Create"/>

        </form>
    </div>
</div>