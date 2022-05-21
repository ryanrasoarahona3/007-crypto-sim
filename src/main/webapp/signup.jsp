<%@ page import="java.util.ArrayList" %>
<%@ page import="com.crypto.cryptosim.structures.InputError" %>
<%@ page import="com.crypto.cryptosim.models.Message" %>
<%@ page import="com.crypto.cryptosim.structures.Info" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="login"/>
</jsp:include>
<%
    // TODO: combiner les deux méthodes
    ArrayList<InputError> errors = (ArrayList<InputError>) request.getAttribute("errors");
    ArrayList<Info> infos = (ArrayList<Info>) request.getSession().getAttribute("message");
    if(errors == null) errors = new ArrayList<>();
    if(infos == null) infos = new ArrayList<>();
%>

<div class="card my-5">
    <div class="card-body">
        <h2>Crypto simulation - Sign up</h2>
        <form method="post" action="signup">
            <div class="mb-3">
                <label for="firstname" class="form-label">Firstname</label>
                <input type="text" class="form-control" id="firstname" name="firstname">
                <% if(errors.contains(InputError.SIGNUP_FIRSTNAME_REQUIRED)) { %>
                <p class="text-danger" style="font-size: .8em;">
                    Password doesn't match
                </p>
                <% } %>
            </div>
            <div class="mb-3">
                <label for="lastname" class="form-label">Lastname</label>
                <input type="text" class="form-control" id="lastname" name="lastname">
                <% if(errors.contains(InputError.SIGNUP_LASTNAME_REQUIRED)) { %>
                <p class="text-danger" style="font-size: .8em;">
                    Password doesn't match
                </p>
                <% } %>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" name="email">
                <% if(errors.contains(InputError.SIGNUP_EMAIL_INVALID)) { %>
                <p class="text-danger" style="font-size: .8em;">
                    Password doesn't match
                </p>
                <% } %>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password">
                <% if(errors.contains(InputError.SIGNUP_PASSWORD_TOO_SHORT)) { %>
                <p class="text-danger" style="font-size: .8em;">
                    Password doesn't match
                </p>
                <% } %>
            </div>
            <div class="mb-3">
                <label for="passwordConfirm" class="form-label">Confirm password</label>
                <input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm">
                <% if(errors.contains(InputError.SIGNUP_PASSWORD_MISMATCHED)) { %>
                    <p class="text-danger" style="font-size: .8em;">
                        Password doesn't match
                    </p>
                <% } %>
            </div>
            <p>You already have an account ? <a href="#">Sign in</a></p>
            <input type="submit" class="btn btn-primary" value="S'inscrire"/>
        </form>
    </div>
</div>


