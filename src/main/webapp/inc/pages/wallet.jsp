<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<%@ page import="com.crypto.cryptosim.ValuableCrypto" %>
<%@ page import="com.crypto.cryptosim.MarketManager" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="com.crypto.cryptosim.services.TransactionManager" %>
<%@ page import="com.crypto.cryptosim.structures.ChartData" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.1/dist/chart.min.js"></script>
<%
    DatabaseManager.getInstance().init(request.getServletContext());
    User u = SessionManager.getInstance().getActiveUser(request);
    TransactionManager trm = TransactionManager.getInstance();
    ArrayList<ValuableCrypto> cryptos = MarketManager.getInstance().getAll();
%>

<div class="my-2">
    <h2>Mon Wallet</h2>
    <div class="row my-3">
        <div class="col-md-6">
            <div class="my-5">
                <h6>Solde du compte : <%= trm.getBalance(u) %> €</h6>
                <h6>Total des cryptoactifs : <%= trm.totalCryptoValues(u) %> €</h6>
                <h6>Fluctuation : <%= 100  * trm.getWeeklyTrend(u) %> % ces 7 derniers jours</h6>
                <h6>Gain : <%= trm.getWeeklyTrend(u) * trm.totalCryptoValues(u) %> €</h6>
            </div>
        </div>
        <div class="col-md-6">
            <canvas id="crypto-pie"></canvas>
        </div>
    </div>
</div>

<div class="my-2">
    <h3>Convertisseur</h3>
    <p>
        Convertissez rapidement votre monnaie classique en crypto (ou vice-verça) grace à cet outil.
    </p>
    <div class="row my-3">
        <div class="col-md-6">
            <div class="input-group">
                <input type="number" class="form-control" placeholder="0" id="money-value"/>
                <select class="form-select" id="money-symbol">
                    <option value="3">€</option>
                </select>
            </div>
        </div>
        <div class="col-md-6">
            <!-- TODO: Revoir le code CSS -->
            <div class="input-group form-row">
                <input type="number" class="form-control col-md-8" placeholder="0" id="crypto-value"/>
                <select class="form-select col-md-4" id="crypto-symbol">
                    <%
                        for(int i = 0; i < cryptos.size(); i++){
                    %>
                        <option value="<%= cryptos.get(i).getId() %>"><%= cryptos.get(i).getSlug() %></option>
                    <%
                        }
                    %>
                </select>
            </div>
        </div>
    </div>
    <div class="row my-3">
        <div class="col-md-6">
            <a href="#" class="btn btn-danger" id="sell-button">Vendre <span class="crypto-n"></span> <span class="crypto-slug"></span></a>
        </div>
        <div class="col-md-6">
            <a href="#" class="btn btn-primary" id="buy-button">Acheter <span class="crypto-n"></span> <span class="crypto-slug"></span></a>
            <p>
                Un gain de <span id="gain"></span> € peut être envisagé en une semaine
            </p>
        </div>
    </div>
</div>


<!-- TODO: bug dans le tableau (les dates) -->
<div class="my-2">
    <h3>Mes transactions</h3>
    <%
        ArrayList<ArrayList<String>> details = trm.getTransactionDetails(u);
    %>
    <table class="table">
        <thead>
            <tr>
                <th scope="col">Date</th>
                <th scope="col">Numéro Transaction</th>
                <th scope="col">Description</th>
                <th scope="col">Débit</th>
                <th scope="col">Crédit</th>
            </tr>
        </thead>
        <tbody>
        <%
            for(int i = 0; i < details.size(); i++){
                ArrayList<String> row = details.get(i);
        %>
        <tr>
            <th scope="row"><%=row.get(0)%></th>
            <td><%=row.get(1)%></td>
            <td><%=row.get(2)%></td>
            <td><%=row.get(3)%></td>
            <td><%=row.get(4)%></td>
        </tr>
        <%
            }
        %>
        <tr>
            <th scope="row">1</th>
            <td>TRA_000005</td>
            <td>ACHAT DE 5 BTC</td>
            <td></td>
            <td>800 €</td>
        </tr>
        </tbody>
    </table>
</div>


<script>
    window.addEventListener("load", ()=>{
        // Pour le convertisseur
        const change = <%
            Map<Integer, Integer> change = new HashMap<>();
            for(int i = 0; i < cryptos.size(); i++){
                ValuableCrypto c = cryptos.get(i);
                change.put(c.getId(), c.getValue());
            }
        %><%= new Gson().toJson(change) %>

        const slugs = <%
            Map<Integer, String> slugs = new HashMap<>();
            for(int i = 0; i < cryptos.size(); i++){
                ValuableCrypto c = cryptos.get(i);
                slugs.put(c.getId(), c.getSlug());
            }
        %><%= new Gson().toJson(slugs) %>

        const fluct = <%
            Map<Integer, Float> fluct = new HashMap<>();
            for(int i = 0; i < cryptos.size(); i++){
                ValuableCrypto c = cryptos.get(i);
                fluct.put(c.getId(), trm.getWeeklyTrend(c));
            }
        %><%= new Gson().toJson(fluct) %>

        function genericRefresh(){
            let cryptoN = Math.round(parseFloat($('#crypto-value').val()))
            let cryptoId = parseInt($('#crypto-symbol').val())

            console.log(parseFloat($('#crypto-value').val()))
            console.log(cryptoN)
            console.log($('#crypto-symbol').val())
            console.log(cryptoId)
            $('.crypto-n').text(cryptoN)
            $('.crypto-slug').text(slugs[$('#crypto-symbol').val()])

            $('#gain').text(parseInt($('#money-value').val()) * fluct[$('#crypto-symbol').val()]);

            $('#buy-button').attr('href', 'crypto-transaction?action=buy&n='+cryptoN+'&crypto='+cryptoId )
            $('#sell-button').attr('href', 'crypto-transaction?action=sell&n='+cryptoN+'&crypto='+cryptoId )
        }

        function refreshMoney(){
            let value = parseInt($('#crypto-value').val()) * change[$('#crypto-symbol').val()]
            $('#money-value').val(value);
            genericRefresh();
        }

        function refreshCrypto(){
            let value = parseInt($('#money-value').val()) / change[$('#crypto-symbol').val()]
            $('#crypto-value').val(value);
            genericRefresh();
        }

        $('#money-value,#money-symbol').change(()=>{
            refreshCrypto();
        })
        $('#crypto-value,#crypto-symbol').change(()=>{
            refreshMoney();
        })

        $('#money-value,#money-symbol').keyup(()=>{
            refreshCrypto();
        })
        $('#crypto-value,#crypto-symbol').keyup(()=>{
            refreshMoney();
        })


        $('#money-symbol').select(()=>{
            refreshCrypto();
        })
        $('#crypto-symbol').select(()=>{
            refreshMoney();
        })

    });
</script>
<script>
    <%
        ChartData chartData = trm.getWalletChartData(u);
    %>
    window.addEventListener("load", ()=>{
        const ctx = document.getElementById('crypto-pie').getContext('2d');
        const myChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: <%= new Gson().toJson(chartData.labels) %>,
                datasets: [{
                    label: 'My First Dataset',
                    data: <%= chartData.data %>,
                    /*backgroundColor: [
                        'rgb(255, 99, 132)',
                        'rgb(54, 162, 235)',
                        'rgb(255, 205, 86)'
                    ],*/
                    hoverOffset: 4
                }]
            },
            options: {
                plugins: {
                    title: {
                        display: true,
                        text: "Mes cryptoactifs"
                    }
                }
            }
        })
    })
</script>