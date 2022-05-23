<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.crypto.cryptosim.structures.InputError" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.crypto.cryptosim.structures.Info" %>
<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="com.crypto.cryptosim.services.TransactionManager" %>
<%@ page import="com.crypto.cryptosim.MarketManager" %>
<%@ page import="com.crypto.cryptosim.ValuableCrypto" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="com.crypto.cryptosim.services.WalletDAO" %>
<%@ page import="com.crypto.cryptosim.models.Wallet" %>
<%@ page import="com.crypto.cryptosim.services.OperationManager" %>
<%
    DatabaseManager.getInstance().init(request.getServletContext());
    User activeUser = SessionManager.getInstance().getActiveUser(request);
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

<div class="card my-3">
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

<div class="card my-3">
    <div class="row">
        <div class="col-sm-6">
            <form method="post" action="wallet">
                <strong>Make a deposit</strong>
                <div class="my-2">
                    <input type="hidden" name="action" value="deposit"/>

                    <label for="credit" class="form-label">How much do you credit ?</label>
                    <input type="number" class="form-control" id="credit" name="sum">
                    <% if(infos.contains(Info.WALLET_DEPOSIT_DONE)) { %>
                    <p class="text-success" style="font-size: .8em;">
                        Deposit done
                    </p>
                    <% } %>
                </div>
                <div class="my-2">
                    <input type="submit" class="btn btn-primary" value="Submit">
                </div>
            </form>
        </div>
        <div class="col-sm-6">
            <form method="post" action="wallet">
                <strong>Make a withdrawal</strong>
                <div class="my-2">
                    <input type="hidden" name="action" value="withdrawal"/>

                    <label for="debit" class="form-label">How much do you want to withdraw from your acount ?</label>
                    <input type="number" class="form-control" id="debit" name="sum">

                    <% if(errors.contains(InputError.WALLET_WITHDRAWAL_NOT_ENOUGH_CASH)) { %>
                    <p class="text-danger" style="font-size: .8em;">
                        Insufficient balance, please, make a deposit
                    </p>
                    <% } %>
                    <% if(infos.contains(Info.WALLET_WITHDRAWAL_DONE)) { %>
                    <p class="text-success" style="font-size: .8em;">
                        Deposit done
                    </p>
                    <% } %>

                    <% if(infos.contains(Info.WITHDRAWAL_DONE)) { %>
                    <p class="text-success" style="font-size: .8em;">
                        Withdrawal done
                    </p>
                    <% } %>

                </div>
                <div class="my-2">
                    <input type="submit" class="btn btn-primary" value="Submit">
                </div>
            </form>
        </div>
    </div>
</div>

<div class="card my-3">
    <div class="card-body">
        <h4>My wallets</h4>


        <% if(infos.contains(Info.WALLET_TRANSACTION_DONE)){ %>
        <div class="my-3 text-success">
            <p>
                Transaction has been done
            </p>
        </div>
        <% } %>

        <% if(errors.contains(InputError.WALLET_NOT_ENOUGH_BALANCE)) { %>
        <div class="my-3 text-danger" style="font-size: .8em;">
            Not enough balance for performing this operation, please make a deposit
        </div>
        <% } %>

        <% if(errors.contains(InputError.WALLET_NOT_ENOUGH_CRYPTO)) { %>
        <div class="my-3 text-danger" style="font-size: .8em;">
            You do not have too much crypto to sell
        </div>
        <% } %>

        <div class="accordion" id="accordionExample">
        <%
            ArrayList<Wallet> myWallets = WalletDAO.getInstance().walletsByUser(activeUser);
            for(int i = 0; i < myWallets.size(); i++){
                Wallet w = myWallets.get(i);
                ValuableCrypto c = MarketManager.getInstance().cryptoById(w.getCryptoId());
                int totalPurchased = OperationManager.getInstance().numberOfCoins(w);
        %>
            <div class="accordion-item">
                <h4 class="accordion-header" id="notifications">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse<%=w.getId()%>" aria-expanded="true" aria-controls="collapse<%=w.getId()%>">
                        <%=w.getName()%> - <%=totalPurchased%> <%=c.getSlug()%>
                    </button>
                </h4>
            </div>
            <div id="collapse<%=w.getId()%>" class="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <p>
                        Actual price of <%=c.getName()%> : <%=c.getValue()%> €
                    </p>
                    <form method="post" action="wallet">
                        <input type="hidden" name="action" value="buy_crypto"/>
                        <input type="hidden" name="wallet" value="<%=w.getId()%>"/>
                        <div class="input-group mb-3">
                            <input type="number" class="form-control" placeholder="Hom many <%=c.getName()%> do you want ?" aria-describedby="buy-<%=w.getId()%>" name="n">
                            <span class="input-group-text" id="buy-<%=w.getId()%>">BTC = 15321 €</span>
                            <button type="submit" class="btn btn-primary" type="button">Buy</button>
                        </div>
                    </form>
                    <form method="post" action="wallet">
                        <input type="hidden" name="action" value="sell_crypto"/>
                        <input type="hidden" name="wallet" value="<%=w.getId()%>"/>
                        <div class="input-group mb-3">
                            <input type="number" class="form-control" placeholder="Hom many <%=c.getName()%> do you want ?" aria-describedby="sell-<%=w.getId()%>" name="n">
                            <span class="input-group-text" id="sell-<%=w.getId()%>">BTC = 15321 €</span>
                            <button type="submit" class="btn btn-danger" type="button">Sell</button>
                        </div>
                    </form>
                </div>
            </div>
        <%
            }
        %>
        </div>
    </div>
</div>