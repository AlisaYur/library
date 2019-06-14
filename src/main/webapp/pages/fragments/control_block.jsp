<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="localization"/>
<div class="control_block">
    <form class="filter_form"
          action="${pageContext.request.contextPath}/mainMenu?action=FilterBooks" method="post">
        <h4><i class="material-icons">filter_list</i><fmt:message key="control_block.header"/></h4>
        <ul class="collapsible filter_panel">
            <jsp:useBean id="genres" scope="request" type="java.util.List"/>
            <li class="list_control control_genres">
                <div class="collapsible-header"><i
                        class="material-icons">filter_drama</i><fmt:message
                        key="control_block.genres"/></div>
                <div class="collapsible-body">
                    <c:forEach items="${genres}" var="genre">
                        <div class="collection-item">
                            <p>
                                <label>
                                    <input type="checkbox" id="${genre.name}" name="${genre.name}"
                                           value="${genre.name}"/>
                                    <span class="span_control genre_name">${genre.name}</span>
                                </label>
                            </p>
                        </div>
                    </c:forEach>
                </div>
            </li>

            <jsp:useBean id="authors" scope="request" type="java.util.List"/>
            <li class="list_control control_authors">
                <div class="collapsible-header"><i class="material-icons">person</i><fmt:message
                        key="control_block.authors"/>
                </div>
                <div class="collapsible-body">
                    <c:forEach items="${authors}" var="author">
                        <div class="collection-item">
                            <p>
                                <label>
                                    <input type="checkbox" id="author_checkbox_${author.lastName}"
                                           name="${author.lastName}"
                                           value="${author.firstName} ${author.lastName}"/>
                                    <span class="span_control authors_name">${author.firstName} ${author.lastName}</span>
                                </label>
                            </p>
                        </div>
                    </c:forEach>
                </div>
            </li>
        </ul>
        <input id="btn_control_filter" type="submit" hidden/>
        <label for="btn_control_filter">
            <a class="btn-floating btn-large waves-effect waves-light light-green btn_filtering">
                <i class="material-icons">search</i>
            </a>
        </label>
    </form>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/controlblock/saveCheckBoxStatus.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/book/removeChipCheckbox.js"></script>
    <script>
      $(document).ready(function () {
        $('.collapsible').collapsible();
      });
    </script>
</div>
