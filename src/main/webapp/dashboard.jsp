<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page import="com.crypto.cryptosim.services.TransactionManager" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<%@ page import="com.crypto.cryptosim.ValuableCrypto" %>
<%@ page import="com.crypto.cryptosim.MarketManager" %>
<%@ page import="com.crypto.cryptosim.structures.ChartData" %>
<%@ page import="com.crypto.cryptosim.PriceCurve" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.crypto.cryptosim.structures.InputError" %>
<%@ page import="com.crypto.cryptosim.structures.Info" %>
<%@ page import="java.util.ArrayList" %>


<%
    // TODO: combiner les deux méthodes
    ArrayList<InputError> errors = (ArrayList<InputError>) request.getSession().getAttribute("errors");
    ArrayList<Info> infos = (ArrayList<Info>) request.getSession().getAttribute("infos");
    if(errors == null) errors = new ArrayList<>();
    if(infos == null) infos = new ArrayList<>();
    request.getSession().setAttribute("errors", null);
    request.getSession().setAttribute("infos", null);
%>

<%
    DatabaseManager.getInstance().init(request.getServletContext());
    User user = SessionManager.getInstance().getActiveUser(request);
%>

<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="dashboard"/>
</jsp:include>

<script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.1/dist/chart.min.js"></script>
<div class="row">
    <div class="col-md-3">
        <jsp:include page="inc/menu.jsp">
            <jsp:param name="page" value="dashboard"/>
        </jsp:include>
    </div>
    <div class="col-md-9">
        <!--
            TODO: à mettre dans le topmenu
        <h1>
            Bonjour <%= SessionManager.getInstance().getActiveUser(request).getFirstname() %>
        </h1>
        -->
        <div class="card my-2">
            <div class="card-body">
                <div class="row">
                    <div class="col-sm-12">
                        <h4>Dashboard</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <p class="my-2">Your balance is <span class="fst-italic"><%= TransactionManager.getInstance().getBalance(user) %> €</span></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6">
                        <form method="post" action="dashboard">
                            <strong>Make a deposit</strong>
                            <div class="my-2">
                                <label for="credit" class="form-label">Amount</label>
                                <input type="number" class="form-control" id="credit" name="sum">
                                <input type="hidden" name="action" value="deposit"/>
                                <% if(infos.contains(Info.DEPOSIT_DONE)) { %>
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
                        <form method="post" action="dashboard">
                            <strong>Débiter votre compte</strong>
                            <div class="my-2">
                                <label for="debit" class="form-label">Amount</label>
                                <input type="number" class="form-control" id="debit" name="sum">
                                <input type="hidden" name="action" value="withdrawal"/>

                                <% if(errors.contains(InputError.WITHDRAWAL_INSUFFICIENT_BALANCE)) { %>
                                <p class="text-danger" style="font-size: .8em;">
                                    Insufficient balance, please, make a deposit
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
        </div>
        <div class="card my-2">
            <div class="card-body">
                <%
                    ValuableCrypto c = MarketManager.getInstance().cryptoByName("Bitcoin");
                    ChartData chartData = PriceCurve.getInstance().getLastNDaysVariation(c, 30);
                %>
                <h3>Cryptocurrency of the day : Bitcoin</h3>
                <canvas id="chart-<%= c.getId() %>-30" width="350" height="200"></canvas>
                <script>
                    window.addEventListener("load", ()=> {
                        Chart.defaults.elements.point.radius = 2;

                        let data = <%= new Gson().toJson(chartData.data) %>;
                        let labels = <%= new Gson().toJson(chartData.labels) %>;
                        let colors = <%= new Gson().toJson(chartData.colors) %>;
                        const ctx = document.getElementById('chart-<%= c.getId() %>-30').getContext('2d');
                        const myChart = new Chart(ctx, {
                            type: 'line',
                            data: {
                                labels: labels,
                                datasets: [{
                                    label: 'BTC-USD',
                                    data: data,
                                    borderColor: colors,
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                scales: {
                                    /*y: {
                                        beginAtZero: true
                                    }*/
                                },
                                plugins: {
                                    title: {
                                        display: true,
                                        text: "The Bitcoin during 30 latest days"
                                    }
                                }
                            }
                        });
                    })
                </script>
            </div>
        </div>
        <div class="card my-2">
            <div class="card-body">
                <h3>Latest news from crypto universe</h3>
                <div class="row my-3">
                    <div class="col-md-4">
                        <h4>« Au Salvador, le rêve autour du bitcoin est en train de s’évanouir »</h4>
                        <p>
                            En plus de faire courir de graves risques financiers, la cryptomonnaie, devenue devise officielle dans le pays d’Amérique latine, répond aux attentes d’une classe moyenne émergente mais écarte les plus âgés et les plus défavorisés, explique Julien Bouissou dans sa chronique.
                        </p>
                        <p class="text-center">
                            <a href="#" class="btn btn-sm btn-outline-primary px-5">Lire</a>
                        </p>
                    </div>
                    <div class="col-md-4">
                        <h4>« La guerre en Ukraine va accélérer l’ascension du yuan à l’international et le déclin du dollar roi »</h4>
                        <p>
                            Dans un entretien au « Monde », l’économiste Michel Aglietta explique comment l’émergence des monnaies numériques de banques centrales peut rebattre les cartes du système monétaire international.
                        </p>
                        <p class="text-center">
                            <a href="#" class="btn btn-sm btn-outline-primary px-5">Lire</a>
                        </p>
                    </div>
                    <div class="col-md-4">
                        <h4>Au Canada, le populiste qui veut remplacer le dollar par le bitcoin</h4>
                        <p>
                            Pierre Poilièvre, candidat à la direction du Parti conservateur, aimerait faire du pays la capitale mondiale de la blockchain. Il promeut un discours anti-establishment faisant écho aux frustrations exprimées par le mouvement du « convoi de la liberté ».
                        </p>
                        <p class="text-center">
                            <a href="#" class="btn btn-sm btn-outline-primary px-5">Lire</a>
                        </p>
                    </div>

                </div>
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