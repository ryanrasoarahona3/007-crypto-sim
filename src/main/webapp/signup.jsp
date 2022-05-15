<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="login"/>
</jsp:include>

<div class="card my-5">
    <div class="card-body">
        <h2>Crypto simulation - Sign up</h2>
        <form method="post" action="signup">
            <div class="mb-3">
                <label for="firstname" class="form-label">Firstname</label>
                <input type="text" class="form-control" id="firstname" name="firstname">
            </div>
            <div class="mb-3">
                <label for="lastname" class="form-label">Lastname</label>
                <input type="text" class="form-control" id="lastname" name="lastname">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" name="email">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
            <div class="mb-3">
                <label for="passwordConfirm" class="form-label">Confirm password</label>
                <input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm">
            </div>
            <p>You already have an account ? <a href="#">Sign in</a></p>
            <input type="submit" class="btn btn-primary" value="S'inscrire"/>
        </form>
    </div>
</div>


