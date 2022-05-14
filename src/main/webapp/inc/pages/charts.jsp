<%@ page import="com.crypto.cryptosim.controllers.ChartsController" %>
<canvas id="myChart" width="350" height="200"></canvas>
<script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.1/dist/chart.min.js"></script>

<script>
    window.addEventListener("load", ()=>{
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