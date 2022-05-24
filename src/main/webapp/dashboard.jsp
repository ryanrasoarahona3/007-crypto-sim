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
                        <h4>« Has govt gone too far by slapping a 1% TDS on transfers of virtual digital assets? »</h4>
                        <p>
                            A back-of-the-envelope calculations suggest that a trader will be left with 60 per cent of their initial capital after TDS on 50 trades. This sucks out liquidity from the crypto ecosystem
                        </p>
                        <p class="text-center">
                            <a href="https://economictimes.indiatimes.com/opinion/et-commentary/has-govt-gone-too-far-by-slapping-a-1-tds-on-transfers-of-virtual-digital-assets/articleshow/91081387.cms" class="btn btn-sm btn-outline-primary px-5">Lire</a>
                        </p>
                    </div>
                    <div class="col-md-4">
                        <h4>« ETMarkets Crypto Conclave: Is crypto to be considered seriously as an asset class? »</h4>
                        <p>
                            Bitcoin specifically has become an asset class due to people seeking long-term gains rather than speculative trading. The decentralised network cannot be missed either, which in itself is a revolutionising entity that may soon overtake social media and other platforms.
                        </p>
                        <p class="text-center">
                            <a href="https://economictimes.indiatimes.com/markets/cryptocurrency/etmarkets-crypto-conclave-is-crypto-to-be-considered-seriously-as-an-asset-class/articleshow/90568319.cms" class="btn btn-sm btn-outline-primary px-5">Lire</a>
                        </p>
                    </div>
                    <div class="col-md-4">
                        <h4>« Bitcoin miners want to recast themselves as eco-friendly »</h4>
                        <p>
                            Facing intense criticism, the crypto mining industry is trying to change the view that its energy-guzzling computers are harmful to the climate.
                        </p>
                        <p class="text-center">
                            <a href="https://economictimes.indiatimes.com/tech/technology/bitcoin-miners-want-to-recast-themselves-as-eco-friendly/articleshow/90411223.cms" class="btn btn-sm btn-outline-primary px-5">Lire</a>
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