<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.crypto.cryptosim.controllers.ChartsController" %>
<%@ page import="com.crypto.cryptosim.ValuableCrypto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.crypto.cryptosim.MarketManager" %>
<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.crypto.cryptosim.PriceCurve" %>
<%@ page import="com.crypto.cryptosim.structures.ChartData" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.crypto.cryptosim.services.TransactionManager" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>

<script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.1/dist/chart.min.js"></script>


<div class="accordion" id="accordionExample">
    <%
        DatabaseManager.getInstance().init(request.getServletContext());
        ArrayList<ValuableCrypto> cryptos = MarketManager.getInstance().getAll();
        User u = SessionManager.getInstance().getActiveUser(request);

        // Normalement, cela devrait marcher avec la branche session
        for(int i = 0; i < cryptos.size() && i < 3; i++){
            ValuableCrypto c = cryptos.get(i);
    %>
    <div class="accordion-item">
        <h2 class="accordion-header" id="notifications">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseNotification" aria-expanded="true" aria-controls="collapseNotification">
                <%= c.getName() %>
            </button>
        </h2>
        <div id="collapseNotification" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
            <div class="accordion-body">
                <div class="row">
                    <div class="col-sm-6">
                        <canvas id="chart-<%= c.getId() %>-7" width="350" height="200"></canvas>
                    </div>
                    <div class="col-sm-6 pt-5">
                        <h6>Prix actuel : <%= c.getValue() %> €</h6>
                        <h6>Dans votre wallet : <%= TransactionManager.getInstance().numberOfCoins(u, c) %> <%= c.getSlug() %></h6>
                        <h6>Total des actifs : <%= (TransactionManager.getInstance().numberOfCoins(u, c) * c.getValue()) %> €</h6>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6">
                        <canvas id="chart-<%= c.getId() %>-30" width="350" height="200"></canvas>
                    </div>
                    <div class="col-sm-6">
                        <canvas id="chart-<%= c.getId() %>-90" width="350" height="200"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%
        }
    %>
    <!--
    <div class="accordion-item">
        <h2 class="accordion-header" id="aboutUs">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseAboutus" aria-expanded="true" aria-controls="collapseAboutus">
                About Us
            </button>
        </h2>
        <div id="collapseAboutus" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
            <div class="accordion-body">
                <p>Hello world</p>
            </div>
        </div>
    </div>
    -->
</div>


<script>
    window.addEventListener("load", ()=>{
        <%
            for(int i = 0; i < cryptos.size(); i++){
                ValuableCrypto c = cryptos.get(i);
                for(int j = 0; j < 3; j++){
                    int numberOfDays = (new int[]{7, 30, 90})[j];
                    ChartData chartData = PriceCurve.getInstance().getLastNDaysVariation(c, numberOfDays);
        %>
                    (()=>{ // JS Encapsulation
                        let data = <%= new Gson().toJson(chartData.data) %>;
                        let labels = <%= new Gson().toJson(chartData.labels) %>;
                        console.log("Hello world");
                        const ctx = document.getElementById('chart-<%= c.getId() %>-<%= numberOfDays %>').getContext('2d');
                        const myChart = new Chart(ctx, {
                            type: 'line',
                            data: {
                                labels: labels,
                                datasets: [{
                                    label: 'USD',
                                    data: data,
                                    borderColor: 'rgb(75, 192, 192)',
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                scales: {
                                    y: {
                                        beginAtZero: true
                                    }
                                },
                                plugins: {
                                    title: {
                                        display: true,
                                        text: "Durant les <%= numberOfDays %> derniers jours"
                                    }
                                }
                            }
                        });
                    })();
        <%
                }
            }
        %>
    })


</script>