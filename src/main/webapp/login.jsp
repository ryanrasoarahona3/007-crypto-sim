<%@ page import="com.crypto.cryptosim.structures.InputError" %>
<%@ page import="com.crypto.cryptosim.structures.Info" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="login"/>
</jsp:include>
<%
    // TODO: combiner les méthodes suivantes
    ArrayList<InputError> errors = (ArrayList<InputError>) request.getAttribute("errors");
    ArrayList<Info> infos = (ArrayList<Info>) request.getSession().getAttribute("infos");
    ArrayList<InputError> session_errors = (ArrayList<InputError>) request.getSession().getAttribute("errors");
    request.getSession().setAttribute("infos", null);
    request.getSession().setAttribute("errors", null);
    if(errors == null) errors = new ArrayList<>();
    if(infos == null) infos = new ArrayList<>();
    if(session_errors == null) session_errors = new ArrayList<>();
%>
<div class="card my-5">
    <div class="card-body">
        <h2>Crypto simulation - Login</h2>
        <% if(infos.contains(Info.SIGNUP_ACCOUNT_CREATED)){ %>
            <div class="my-3 text-success">
                <p>
                    Your account is successfully created
                </p>
            </div>
        <% } %>
        <% if(session_errors.contains(InputError.SESSION_EXPIRED)){ %>
        <div class="my-3 text-danger">
            <p>
                Session expired, please log in to continue
            </p>
        </div>
        <% } %>
        <p>
            Login to the best crypto application simulator
        </p>
        <form method="post" action="login">
            <!-- TODO: Use Enum or Class -->
            <!-- TODO: Use errors instead of error -->
            <% if(request.getAttribute("error") == "CREDENTIAL_MISMATCHED") { %>
                <div class="mb-3 text-danger">
                    <p>Authentification error, please retry again</p>
                </div>
            <% } %>
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" name="email">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
            <p>You don't have an account ? <a href="#">Create one for free</a></p>
            <button type="submit" class="btn btn-primary">Login</button>
        </form>
    </div>
</div>

<jsp:include page="inc/footer.jsp">
    <jsp:param name="page" value="login"/>
</jsp:include>