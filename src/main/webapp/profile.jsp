<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="profile"/>
</jsp:include>

<div class="row">
    <div class="col-md-3">
        <jsp:include page="inc/menu.jsp">
            <jsp:param name="page" value="profile"/>
        </jsp:include>
    </div>
    <div class="col-md-9">
        <jsp:include page="inc/pages/charts.jsp">
            <jsp:param name="page" value="profile"/>
        </jsp:include>
    </div>
</div>

<jsp:include page="inc/footer.jsp">
    <jsp:param name="page" value="profile"/>
</jsp:include>