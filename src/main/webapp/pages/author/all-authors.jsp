<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/error.tld" prefix="e" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="localization"/>
<html>
<head>
    <title><fmt:message key="authors.title"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"
          type="text/css"/>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/author/validation.js"></script>
</head>
<body>
<script>
  $(document).ready(function () {
    (function ($) {
      $('#author_id_search').keyup(function () {

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
<div class="author_body_wrap">
    <div class="left_bar_fragment">
        <jsp:include page="/pages/fragments/leftbar.jsp"/>
    </div>
    <div class="container">
        <input id="author_id_search" type="text" name="search_authors" autocomplete="off"
               placeholder="<fmt:message key="search"/>">
        <table id="readers-table">
            <thead>
            <tr>
                <th class="center"><fmt:message key="authors.table.first_name"/></th>
                <th class="center"><fmt:message key="authors.table.last_name"/></th>
                <th class="center"><fmt:message key="authors.table.operations"/></th>
            </tr>
            </thead>
            <tbody id="books-body" class="searchable">
            <%--@elvariable id="authors" type="java"--%>
            <c:forEach items="${authors}" var="author">
                <tr id="${author.id}">
                    <td class="center">${author.firstName}</td>
                    <td class="center">${author.lastName}</td>
                    <td class="operations center">
                        <form method="POST" class="delete-reader-form-${author.id}"
                              action="${pageContext.request.contextPath}/mainMenu?action=DeleteAuthor&id=${author.id}">
                            <c:choose>
                                <c:when test="${fn:length(author.books)>0}">
                                    <button type="submit" class="btn disabled"><i
                                            class="material-icons">delete</i>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button id="confirmDelete-${author.id}" type="submit"
                                            class="btn"
                                            onclick="deleteReader(${author.id})"><i
                                            class="material-icons">delete</i>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                        <a class="btn blue modal-trigger" href="#updateAuthorName"
                           onclick="passDataToUpdateModal('${author.id}', '${author.firstName}', '${author.lastName}')">
                            <i class="large material-icons">mode_edit</i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="fixed-action-btn">
            <a class="btn-floating btn-large red modal-trigger" href="#createAuthor">
                <i class="large material-icons">mode_edit</i>
            </a>
        </div>
        <!-- Confirmation delete modal -->
        <div id="modal4" class="modal">
            <div class="modal-content">
                <h4><fmt:message key="authors.delete.modal"/></h4>
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

        <!-- Create Author Modal -->
        <div id="createAuthor" class="modal">
            <div class="modal-content">
                <h4><fmt:message key="authors.create.modal"/></h4>
                <form action="${pageContext.request.contextPath}/mainMenu?action=SaveAuthor"
                      class="submitAuthor" method="post">
                    <input id="author-create-fname" type="text" name="firstName"
                           placeholder="<fmt:message key="authors.placeholder.fname"/>">
                    <input id="author-create-lname" type="text" name="lastName"
                           placeholder="<fmt:message key="authors.placeholder.lname"/>">
                    <input id="submitAuthor" type="submit" name="btn_create_author"
                           class="btn modal-close" value="<fmt:message key="create"/>"/>
                </form>
            </div>
        </div>

        <!-- Update Author Modal -->
        <div id="updateAuthorName" class="modal">
            <div class="modal-content">
                <h4><fmt:message key="authors.update.modal"/></h4>
                <form action="${pageContext.request.contextPath}/mainMenu?action=UpdateAuthor"
                      class="updateAuthorSubmit"
                      method="post">
                    <input id="author-update-id-name" type="hidden" name="id">
                    <input id="author-update-fname-name" type="text" name="firstName"
                           placeholder="<fmt:message key="authors.placeholder.fname"/>">
                    <input id="author-update-lname-name" type="text" name="lastName"
                           placeholder="<fmt:message key="authors.placeholder.lname"/>">
                    <input id="updateAuthorSubmitName" type="submit" name="btn_update_author"
                           class="btn modal-close" value="<fmt:message key="update"/>"/>
                </form>
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
    $('.fixed-action-btn').floatingActionButton();
    $('.modal').modal();
  });

  function passDataToUpdateModal(id, firstName, lastName) {
    document.getElementById('author-update-id-name').value = id;
    document.getElementById('author-update-fname-name').value = firstName;
    document.getElementById('author-update-lname-name').value = lastName;
  }
</script>
</body>
<jsp:include page="/pages/fragments/modalCreateBook.jsp"/>
</html>
