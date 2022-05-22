<%@ page import="com.crypto.cryptosim.structures.InputError" %>
<%@ page import="com.crypto.cryptosim.structures.Info" %>
<%@ page import="java.util.ArrayList" %>
<div class="pb-3">
    <h2>Settings</h2>
</div>


<%
    ArrayList<InputError> errors = (ArrayList<InputError>) request.getSession().getAttribute("errors");
    ArrayList<Info> infos = (ArrayList<Info>) request.getSession().getAttribute("infos");
    if(errors == null) errors = new ArrayList<>();
    if(infos == null) infos = new ArrayList<>();
    request.getServletContext().removeAttribute("errors");
    request.getServletContext().removeAttribute("infos");
%>


<div class="accordion" id="accordionExample">
    <div class="accordion-item">
        <h2 class="accordion-header" id="notifications">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseNotification" aria-expanded="true" aria-controls="collapseNotification">
                General
            </button>
        </h2>
        <div id="collapseNotification" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
            <div class="accordion-body">
                <form method="post" action="settings">
                    <h3>Update your password</h3>

                    <div class="mb-3">
                        <label for="password" class="form-label">Your current password</label>
                        <input type="password" class="form-control" id="password" name="existingPassword">
                        <% if(errors.contains(InputError.PASSUPDATE_INCORRECT_PASSWORD)) { %>
                        <p class="text-danger" style="font-size: .8em;">
                            Incorrect password
                        </p>
                        <% } %>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Your new password</label>
                        <input type="password" class="form-control" id="password" name="newPassword">
                        <% if(errors.contains(InputError.PASSUPDATE_PASSWORD_TOO_SHORT)) { %>
                        <p class="text-danger" style="font-size: .8em;">
                            Password should contain at least 5 characters
                        </p>
                        <% } %>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Your current password</label>
                        <input type="password" class="form-control" id="password" name="newPasswordConfirm">
                        <% if(errors.contains(InputError.PASSUPDATE_PASSWORD_MISMATCHED)) { %>
                        <p class="text-danger" style="font-size: .8em;">
                            Password doesn't match
                        </p>
                        <% } %>
                    </div>

                    <button type="submit" class="btn btn-primary">Update password</button>
                </form>
            </div>
        </div>
    </div>
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
</div>