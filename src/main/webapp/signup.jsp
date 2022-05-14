<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="login"/>
</jsp:include>

<div class="card my-5">
    <div class="card-body">
        <h2>Crypto simulation - Sign up</h2>
        <form>
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Surname</label>
                <input type="text" class="form-control" id="surname" name="surname">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" name="email">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password">
            </div>
            <div class="mb-3">
                <label for="passwordConfirm" class="form-label">Confirm password</label>
                <input type="password" class="form-control" id="passwordConfirm">
            </div>
            <p>You already have an account ? <a href="#">Sign in</a></p>
            <button type="submit" class="btn btn-primary">Login</button>
        </form>
    </div>
</div>


