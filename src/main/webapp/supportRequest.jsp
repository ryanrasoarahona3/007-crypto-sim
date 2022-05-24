<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.crypto.cryptosim.services.MessageDAO" %>
<%@ page import="com.crypto.cryptosim.DatabaseManager" %>
<%@ page import="com.crypto.cryptosim.services.SessionManager" %>
<%@ page import="com.crypto.cryptosim.services.SupportRequestDAO" %>
<%@ page import="com.crypto.cryptosim.structures.Info" %>
<%@ page import="com.crypto.cryptosim.structures.InputError" %>
<%@ page import="com.crypto.cryptosim.models.*" %>
<%@ page import="com.crypto.cryptosim.services.SupportResponseDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ArrayList<InputError> errors = (ArrayList<InputError>) request.getSession().getAttribute("errors");
    ArrayList<Info> infos = (ArrayList<Info>) request.getSession().getAttribute("infos");
    if(errors == null) errors = new ArrayList<>();
    if(infos == null) infos = new ArrayList<>();
    request.getSession().removeAttribute("errors");
    request.getSession().removeAttribute("infos");
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
                    <input type="hidden" name="action" value="request"/>

                    <% if(infos.contains(Info.SUPPORTREQUEST_SUCCESS)){ %>
                    <div class="my-3 text-success">
                        <p>
                            Your request has been sent to the technical support
                        </p>
                    </div>
                    <% } %>

                    <div class="mb-3">
                        <label for="title" class="form-label">Object</label>
                        <input type="text" class="form-control" id="title" name="title"
                            value="<%= (request.getParameter("title")!=null)?request.getParameter("title"):""%>"
                        >
                        <% if(errors.contains(InputError.SUPPORTREQUEST_EMPTY_TITLE)) { %>
                        <p class="text-danger" style="font-size: .8em;">
                            Required field
                        </p>
                        <% } %>
                    </div>
                    <div class="mb-3">
                        <label for="message" class="form-label">Your message</label>
                        <textarea class="form-control" id="message" rows="3" name="message"><%= (request.getParameter("message")!=null)?request.getParameter("message"):""%></textarea>
                        <% if(errors.contains(InputError.SUPPORTREQUEST_EMPTY_MESSAGE)) { %>
                        <p class="text-danger" style="font-size: .8em;">
                            Required field
                        </p>
                        <% } %>
                    </div>

                    <input type="submit" class="btn btn-primary" value="Envoyer"/>
                </form>
                <hr/>
                <%
                    ArrayList<SupportResponse> responses = SupportResponseDAO.getInstance().getAllSentToUser(user);
                    int l = responses.size();
                %>
                <h3 class="my-5">You have <%=l%> message<%=(l>1)?"s":""%> from the admin</h3>
                <%
                    for(int i = 0; i < responses.size(); i++){
                        SupportResponse r = responses.get(i);
                %>
                <div class="my-3">
                    <h5>Object : <%=r.getTitle()%></h5>
                    <p>
                        <%=r.getMessage()%>
                    </p>
                    <hr/>
                </div>
                <%
                    }
                %>
            </div>
        </div>

        <% } else { %>
        <div class="card">
            <div class="card-body">
                <h2>Received messages</h2>
                <div class="accordion" id="accordionExample">
                    <%
                        ArrayList<ReferencedSupportRequest> messages = SupportRequestDAO.getInstance().getAll();
                        for(int i = 0; i < messages.size(); i++){
                            ReferencedSupportRequest message = messages.get(i);
                    %>
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="message<%=message.getId()%>">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseMessage<%=message.getId()%>" aria-expanded="true" ariaControls="collapseMessage<%=message.getId()%>">
                                <%=message.getTitle()%>
                            </button>
                        </h2>
                        <div id="collapseMessage<%=message.getId()%>" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">
                            <div class="accordion-body">
                                <strong>From : &lt;<%=message.getEmail()%>&gt;</strong>
                                <div>
                                    <p>
                                        <%=message.getMessage()%>
                                    </p>
                                </div>
                                <hr/>
                                <form method="post" action="supportRequest">
                                    <input type="hidden" name="action" value="response"/>
                                    <input type="hidden" name="userId" value="<%=message.getUserId()%>"/>

                                    <% if(infos.contains(Info.SUPPORTRESPONSE_SUCCESS)){ %>
                                    <div class="my-3 text-success">
                                        <p>
                                            Your response has been sent to the site user
                                        </p>
                                    </div>
                                    <% } %>

                                    <div class="mb-3">
                                        <label for="messageTitle" class="form-label">Object</label>
                                        <input type="text" class="form-control form-control-sm" id="messageTitle" name="title"
                                               value="<%= (request.getParameter("title")!=null)?request.getParameter("title"):""%>"
                                        >
                                        <!--<% if(errors.contains(InputError.SUPPORTREQUEST_EMPTY_TITLE)) { %>
                                        <p class="text-danger" style="font-size: .8em;">
                                            Required field
                                        </p>
                                        <% } %>-->
                                    </div>
                                    <div class="mb-3">
                                        <label for="messageBody" class="form-label">Your message</label>
                                        <textarea class="form-control form-control-sm" id="messageBody" rows="3" name="message"><%= (request.getParameter("message")!=null)?request.getParameter("message"):""%></textarea>
                                        <!--<% if(errors.contains(InputError.SUPPORTREQUEST_EMPTY_MESSAGE)) { %>
                                        <p class="text-danger" style="font-size: .8em;">
                                            Required field
                                        </p>
                                        <% } %>-->
                                    </div>

                                    <input type="submit" class="btn btn-primary" value="Répondre"/>
                                </form>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
        <% } %>




    </div>
</div>

<jsp:include page="inc/footer.jsp">
    <jsp:param name="page" value="wallet"/>
</jsp:include>


