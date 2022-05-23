<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.crypto.cryptosim.models.ReferencedMessage" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.crypto.cryptosim.services.MessageDAO" %>
<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="com.crypto.cryptosim.models.ExtendedUser" %>
<%@ page import="com.crypto.cryptosim.services.SupportRequestDAO" %>
<%@ page import="com.crypto.cryptosim.models.ReferencedSupportRequest" %>
<%@ page import="com.crypto.cryptosim.structures.Info" %>
<%@ page import="com.crypto.cryptosim.structures.InputError" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ArrayList<InputError> errors = (ArrayList<InputError>) request.getSession().getAttribute("errors");
    ArrayList<Info> infos = (ArrayList<Info>) request.getSession().getAttribute("infos");
    if(errors == null) errors = new ArrayList<>();
    if(infos == null) infos = new ArrayList<>();
    request.getServletContext().removeAttribute("errors");
    request.getServletContext().removeAttribute("infos");
%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="wallet"/>
</jsp:include>

<div class="row">
    <div class="col-md-3">
        <jsp:include page="inc/menu.jsp">
            <jsp:param name="page" value="wallet"/>
        </jsp:include>
    </div>
    <div class="col-md-9">



        <%
            DatabaseManager.getInstance().init(request.getServletContext());
            ExtendedUser user = (ExtendedUser) SessionManager.getInstance().getActiveUser(request);
        %>

        <% if(!user.isAdmin()) { %>
        <div class="card my-5">
            <div class="card-body">
                <h2>Technical support</h2>
                <form method="post" action="supportRequest">

                    <% if(infos.contains(Info.SUPPORTREQUEST_SUCCESS)){ %>
                    <div class="my-3 text-success">
                        <p>
                            Your request has been sent to the technical support
                        </p>
                    </div>
                    <% } %>

                    <div class="mb-3">
                        <label for="title" class="form-label">Object</label>
                        <input type="text" class="form-control" id="title" name="title">
                    </div>
                    <div class="mb-3">
                        <label for="message" class="form-label">Your message</label>
                        <textarea class="form-control" id="message" rows="3" name="message"></textarea>
                    </div>

                    <input type="submit" class="btn btn-primary" value="Envoyer"/>
                </form>
            </div>
        </div>

        <% } else { %>
        <div class="card">
            <div class="card-body">
                <h2>Received messages</h2>
                <%
                    ArrayList<ReferencedSupportRequest> messages = SupportRequestDAO.getInstance().getAll();
                    for(int i = 0; i < messages.size(); i++){
                        ReferencedSupportRequest message = messages.get(i);
                %>
                <div class="py-2">
                    <strong>From : &lt;<%=message.getEmail()%>&gt;</strong>
                    <div>
                        <strong><%=message.getTitle()%></strong>
                        <p>
                            <%=message.getMessage()%>
                        </p>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <% } %>




    </div>
</div>

<jsp:include page="inc/footer.jsp">
    <jsp:param name="page" value="wallet"/>
</jsp:include>

