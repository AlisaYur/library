<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/error.tld" prefix="e" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Managers</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"
          type="text/css"/>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/manager/switcherStatus.js"></script>
<body>
<script>
  $(document).ready(function () {
    (function ($) {
      $('#input_managers_search').keyup(function () {

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
        <input id="input_managers_search" type="text" name="search_readers" autocomplete="off"
               placeholder="Search">
        <table id="managers-table">
            <thead>
            <tr>
                <th class="center">Name</th>
                <th class="center">Login</th>
                <th class="center">Email</th>
                <th class="center">Active</th>
                <th class="center">Operation</th>
            </tr>
            </thead>
            <tbody id="books-body" class="searchable">
            <%--@elvariable id="managers" type="java"--%>
            <c:forEach items="${managers}" var="manager">
                <tr id="${manager.id}">
                    <td class="center">${manager.name}</td>
                    <td class="center">${manager.login} </td>
                    <td class="center">${manager.email}</td>
                    <!-- Switch -->
                    <td class="center">
                        <div id="sw-reader" class="switch">
                            <label>
                                Disabled
                                <c:choose>
                                    <c:when test="${manager.active=='true'}">
                                        <input type="checkbox" id="manager-active-${manager.id}"
                                               checked
                                               onclick="switchManagerState(${manager.id})">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" id="manager-active-${manager.id}"
                                               onclick="switchManagerState(${manager.id})">
                                    </c:otherwise>
                                </c:choose>
                                <span class="lever"></span>
                                Active
                            </label>
                        </div>
                    </td>
                    <td class="center">
                        <form method="POST" class="delete-manager-form-${manager.id}"
                              action="${pageContext.request.contextPath}/mainMenu?action=DeleteManager&id=${manager.id}">
                            <button id="confirmDelete-${manager.id}" type="submit" class="btn"
                                    onclick="deleteManager(${manager.id})"><i
                                    class="material-icons">delete</i></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="fixed-action-btn">
            <a class="btn-floating btn-large red modal-trigger" href="#createManager">
                <i class="large material-icons">mode_edit</i>
            </a>
        </div>

        <!-- Create Genre Modal -->
        <div id="createManager" class="modal">
            <div class="modal-content">
                <h4>Create new Manager</h4>
                <form action="${pageContext.request.contextPath}/mainMenu?action=CreateManager"
                      class="submitGenre" method="post">
                    <input id="manager-name" type="text" name="managerName" placeholder="Name">
                    <input id="manager-login" type="text" name="managerLogin" placeholder="Login">
                    <input id="manager-email" type="email" name="managerEmail" placeholder="Email">
                    <input id="manager-pass" type="password" name="managerPass"
                           placeholder="Password">
                    <input id="submitManager" type="submit" class="btn" value="Create"/>
                </form>
            </div>
        </div>

        <!-- Confirmation delete modal -->
        <div id="modal4" class="modal">
            <div class="modal-content">
                <h4>Do you really want to delete Librarian?</h4>
            </div>
            <div class="modal-footer">
                <a id="confirm-delete-modal-manager" href="#"
                   class="modal-close waves-effect waves-green btn-flat">Yes</a>
                <a id="refuse-delete-modal" href="#"
                   class="modal-close waves-effect waves-green btn-flat">No</a>
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

  function deleteManager(id) {
    event.preventDefault();
    $('#modal4').modal('open');

    $("#confirm-delete-modal-manager").click(function () {
      $('.delete-manager-form-' + id).submit();
    });
  }
</script>
</body>
<jsp:include page="/pages/fragments/modalCreateBook.jsp"/>
</html>
