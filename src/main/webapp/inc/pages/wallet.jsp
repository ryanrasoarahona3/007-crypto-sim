<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.1/dist/chart.min.js"></script>


<div class="my-2">
    <h2>Mon Wallet</h2>
    <div class="row my-3">
        <div class="col-md-6">
            <div class="my-5">
                <h6>Total des cryptoactifs : 13 €</h6>
                <h6>Variation : 112 % ces 7 derniers jours</h6>
                <h6>Gain : 456 €</h6>
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
                <input type="number" class="form-control" placeholder="0"/>
                <select class="form-select">
                    <option value="3">€</option>
                </select>
            </div>
        </div>
        <div class="col-md-6">
            <!-- TODO: Revoir le code CSS -->
            <div class="input-group form-row">
                <input type="number" class="form-control col-md-8" placeholder="0"/>
                <select class="form-select col-md-4">
                    <option value="3">BTC</option>
                    <option value="3">ETH</option>
                </select>
            </div>
        </div>
    </div>
    <div class="row my-3">
        <div class="col-md-6">
            <a href="#" class="btn btn-danger">Vendre</a>
        </div>
        <div class="col-md-6">
            <a href="#" class="btn btn-primary">Acheter</a>
            <p>
                Un gain de 55 € peut être envisagé en une semaine
            </p>
        </div>
    </div>
</div>

<div class="my-2">
    <h3>Mes transactions</h3>
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
        <tr>
            <th scope="row">1</th>
            <td>TRA_000005</td>
            <td>ACHAT DE 5 BTC</td>
            <td></td>
            <td>800 €</td>
        </tr>
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
        const ctx = document.getElementById('crypto-pie').getContext('2d');
        const myChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: [
                    'Red',
                    'Blue',
                    'Yellow'
                ],
                datasets: [{
                    label: 'My First Dataset',
                    data: [300, 50, 100],
                    backgroundColor: [
                        'rgb(255, 99, 132)',
                        'rgb(54, 162, 235)',
                        'rgb(255, 205, 86)'
                    ],
                    hoverOffset: 4
                }]
            }
        })
    })
</script>