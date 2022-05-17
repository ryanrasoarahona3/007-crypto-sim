<%@ page import="com.crypto.cryptosim.controllers.ChartsController" %>
<%@ page import="com.crypto.cryptosim.ValuableCrypto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.crypto.cryptosim.MarketManager" %>
<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<canvas id="myChart" width="350" height="200"></canvas>
<script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.1/dist/chart.min.js"></script>


<div class="accordion" id="accordionExample">
    <%
        DatabaseManager.getInstance().init(request.getServletContext());
        ArrayList<ValuableCrypto> cryptos = MarketManager.getInstance().getAll();

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
                        <canvas id="chart-<%= c.getId() %>" width="350" height="200"></canvas>
                    </div>
                    <div class="col-sm-6">
                        Two
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
            for(int i = 0; i < cryptos.size() && i < 3; i++){
                ValuableCrypto c = cryptos.get(i);
        %>
        (()=>{ // JS Encapsulation
            let data = <%= ChartsController.getInstance().getExampleJSON() %>;
            let labels = <%= ChartsController.getInstance().getExampleLabelJSON() %>;
            console.log("Hello world");
            const ctx = document.getElementById('chart-<%= c.getId() %>').getContext('2d');
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
                    }
                }
            });
        })();
        <%
            }
        %>


        let data = <%= ChartsController.getInstance().getExampleJSON() %>;
        let labels = <%= ChartsController.getInstance().getExampleLabelJSON() %>;
        console.log("Hello world");
        const ctx = document.getElementById('myChart').getContext('2d');
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
                }
            }
        });
    })
</script>