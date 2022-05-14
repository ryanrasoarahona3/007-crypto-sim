<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="login"/>
</jsp:include>

<div class="card my-5">
    <div class="card-body">
        <h2>Crypto simulation - Login</h2>
        <p>
            Login to the best crypto application simulator
        </p>
        <form>
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" name="email">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password">
            </div>
            <p>You don't have an account ? <a href="#">Create one for free</a></p>
            <button type="submit" class="btn btn-primary">Login</button>
        </form>
    </div>
</div>

<jsp:include page="inc/footer.jsp">
    <jsp:param name="page" value="login"/>
</jsp:include>