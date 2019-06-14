<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="localization"/>
<html>
<head>
    <title><fmt:message key="main.title"/></title>
</head>
<body>
<div class="header_fragment">
    <jsp:include page="/pages/fragments/header.jsp"/>
</div>
<div class="main_wrapper">
    <div class="left_control_block">
        <c:if test="${sessionScope.roles.name eq 'ADMIN_ROLE'}">
            <div class="left_bar_fragment">
                <jsp:include page="/pages/fragments/leftbar.jsp"/>
            </div>
        </c:if>
        <div class="control_block_fragment">
            <jsp:include page="/pages/fragments/control_block.jsp"/>
        </div>
    </div>
    <div class="books_fragment">
        <jsp:include page="/pages/book/all-books.jsp"/>
    </div>
</div>
<div class="footer_fragment">
    <jsp:include page="/pages/fragments/footer.jsp"/>
</div>
</body>
<jsp:include page="/pages/fragments/modalCreateBook.jsp"/>
</html>
