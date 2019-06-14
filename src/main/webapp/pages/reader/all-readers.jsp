<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/error.tld" prefix="e" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="localization"/>
<html>
<head>
    <title><fmt:message key="leftbar.readers"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"
          type="text/css"/>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/reader/switcher.js"></script>
</head>
<body>
<script>
  $(document).ready(function () {
    (function ($) {
      $('#input_readers_search').keyup(function () {

        let rex = new RegExp($(this).val(), 'i');
        $('.searchable tr').hide();
        $('.searchable tr').filter(function () {
          return rex.test($(this).text());
        }).show();
      })
    }(jQuery));
  });
</script>
<div class="header_fragment">
    <jsp:include page="/pages/fragments/header.jsp"/>
</div>
<div class="all_readers_body">
    <div class="left_bar_fragment">
        <jsp:include page="/pages/fragments/leftbar.jsp"/>
    </div>
    <div class="container">
        <input id="input_readers_search" type="text" name="search_readers" autocomplete="off"
               placeholder="<fmt:message key="search"/>">
        <table id="readers-table">
            <thead>
            <tr>
                <th class="center"><fmt:message key="readers.name"/></th>
                <th class="center"><fmt:message key="readers.login"/></th>
                <th class="center"><fmt:message key="readers.email"/></th>
                <th class="center"><fmt:message key="readers.open_orders"/></th>
                <th class="center"><fmt:message key="readers.active"/></th>
                <th class="center"><fmt:message key="readers.operations"/></th>
            </tr>
            </thead>
            <tbody id="books-body" class="searchable">
            <%--@elvariable id="readers" type="java"--%>
            <c:forEach items="${readers}" var="reader">
                <tr id="${reader.id}">
                    <td class="center">${reader.name}</td>
                    <td class="center">${reader.login} </td>
                    <td class="center">${reader.email}</td>
                    <c:set var="countActiveOrders" value="0"/>
                    <c:forEach items="${reader.orders}" var="order">
                        <c:if test="${order.status != 'CLOSE'}">
                            <c:set var="countActiveOrders" value="${countActiveOrders + 1}"/>
                        </c:if>
                    </c:forEach>
                    <td class="center">${countActiveOrders}</td>
                    <!-- Switch -->
                    <td class="center">
                        <div id="sw-reader" class="switch">
                            <label>
                                <fmt:message key="readers.disabled.state"/>
                                <c:choose>
                                    <c:when test="${reader.active=='true'}">
                                        <input type="checkbox" id="reader-active-${reader.id}"
                                               checked
                                               onclick="switchReaderState(${reader.id})">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" id="reader-active-${reader.id}"
                                               onclick="switchReaderState(${reader.id})">
                                    </c:otherwise>
                                </c:choose>
                                <span class="lever"></span>
                                <fmt:message key="readers.active.state"/>
                            </label>
                        </div>
                    </td>
                    <td class="center">
                        <form method="POST" class="delete-reader-form-${reader.id}"
                              action="${pageContext.request.contextPath}/mainMenu?action=DeleteReader&id=${reader.id}">
                            <c:choose>
                                <c:when test="${countActiveOrders > 0}">
                                    <button type="submit" class="btn disabled"><i
                                            class="material-icons">delete</i></button>
                                </c:when>
                                <c:otherwise>
                                    <button id="confirmDelete-${reader.id}" type="submit"
                                            class="btn"
                                            onclick="deleteReader(${reader.id})"><i
                                            class="material-icons">delete</i></button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Confirmation delete modal -->
        <div id="modal4" class="modal">
            <div class="modal-content">
                <h4><fmt:message key="readers.modal.delete.title"/></h4>
            </div>
            <div class="modal-footer">
                <a id="confirm-delete-modal" href="#"
                   class="modal-close waves-effect waves-green btn-flat"><fmt:message
                        key="yes"/></a>
                <a id="refuse-delete-modal" href="#"
                   class="modal-close waves-effect waves-green btn-flat"><fmt:message
                        key="no"/></a>
            </div>
        </div>
    </div>
</div>
<div class="footer_fragment">
    <jsp:include page="/pages/fragments/footer.jsp"/>
</div>
<e:error/>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/error/showErrors.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bin/materialize.min.js"></script>
<script>
  $(document).ready(function () {
    $('#modal4').modal({
          dismissible: false
        }
    );
  });

  function deleteReader(id) {
    event.preventDefault();
    $('#modal4').modal('open');

    $("#confirm-delete-modal").click(function () {
      $('.delete-reader-form-' + id).submit();
    });
  }
</script>
</body>
<jsp:include page="/pages/fragments/modalCreateBook.jsp"/>
</html>
