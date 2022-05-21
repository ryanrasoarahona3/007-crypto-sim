<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page import="com.crypto.cryptosim.services.TransactionManager" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="com.crypto.cryptosim.DatabaseManager" %>

<%
    DatabaseManager.getInstance().init(request.getServletContext());
    User user = SessionManager.getInstance().getActiveUser(request);
%>

<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="dashboard"/>
</jsp:include>

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
                        <h4>Mon Compte</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <p class="my-2">Votre solde est de <span class="fst-italic"><%= TransactionManager.getInstance().getBalance(user) %> €</span></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6">
                        <form method="post" action="credit">
                            <strong>Créditer votre compte</strong>
                            <div class="my-2">
                                <label for="credit" class="form-label">Montant à déposer</label>
                                <input type="number" class="form-control" id="credit" name="credit">
                            </div>
                            <div class="my-2">
                                <input type="submit" class="btn btn-primary" value="Déposer">
                            </div>
                        </form>
                    </div>
                    <div class="col-sm-6">
                        <form method="post" action="debit">
                            <strong>Débiter votre compte</strong>
                            <div class="my-2">
                                <label for="debit" class="form-label">Montant à retirer</label>
                                <input type="number" class="form-control" id="debit" name="debit">
                            </div>
                            <div class="my-2">
                                <input type="submit" class="btn btn-primary" value="Retirer">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="card my-2">
            <div class="card-body">
                Bitcoin chart
            </div>
        </div>
        <div class="card my-2">
            <div class="card-body">
                <h3>Les dernières nouvelles</h3>
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