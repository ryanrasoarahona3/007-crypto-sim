<jsp:include page="inc/header.jsp">
    <jsp:param name="page" value="prices"/>
</jsp:include>

<div class="row">
    <div class="col-md-3">
        <jsp:include page="inc/menu.jsp">
            <jsp:param name="page" value="prices"/>
        </jsp:include>
    </div>
    <div class="col-md-9">
        <jsp:include page="inc/pages/prices.jsp">
            <jsp:param name="page" value="prices"/>
        </jsp:include>
    </div>
</div>

<jsp:include page="inc/footer.jsp">
    <jsp:param name="page" value="prices"/>
</jsp:include>