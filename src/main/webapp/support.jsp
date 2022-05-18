<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.crypto.cryptosim.models.ReferencedMessage" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.crypto.cryptosim.services.MessageDAO" %>
<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page import="com.crypto.cryptosim.models.User" %>
<%@ page import="com.crypto.cryptosim.models.ExtendedUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>




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
                <h2>Support technique</h2>
                <form method="post" action="support">
                    <div class="mb-3">
                        <label for="request" class="form-label">Requête</label>
                        <input type="text" class="form-control" id="request" name="request">
                    </div>
                    <div class="mb-3">
                        <label for="title" class="form-label">Title</label>
                        <input type="text" class="form-control" id="title" name="title">
                    </div>
                    <div class="mb-3">
                        <label for="body" class="form-label">Votre message</label>
                        <textarea class="form-control" id="body" rows="3" name="body"></textarea>
                    </div>

                    <input type="submit" class="btn btn-primary" value="Envoyer"/>
                </form>
            </div>
        </div>

        <% } else { %>
        <div class="card">
            <div class="card-body">
                <h2>Messages reçus</h2>
                <%
                    ArrayList<ReferencedMessage> messages = MessageDAO.getInstance().getTableData();
                    for(int i = 0; i < messages.size(); i++){
                        ReferencedMessage message = messages.get(i);
                %>
                <div class="py-2">
                    <strong>From : &lt;<%=message.getEmail()%>&gt;</strong>
                    <div>
                        <strong><%=message.getRequest()%></strong><br/>
                        <strong><%=message.getTitle()%></strong>
                        <p>
                            <%=message.getBody()%>
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


