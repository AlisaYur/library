<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/error.tld" prefix="e" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="localization"/>
<html>
<head>
    <title><fmt:message key="genres.title"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"
          type="text/css"/>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/genre/validation.js"></script>
</head>
<body>
<script>
  $(document).ready(function () {
    (function ($) {
      $('#genres_id_search').keyup(function () {

        let rex = new RegExp($(this).val(), 'i');
        $('.searchable tr').hide();
        $('.searchable tr').filter(function () {
          return rex.test($(this).text());
        }).show();
      })
    }(jQuery));
  });
</script>
<div id="bla"></div>
<div class="header_fragment">
    <jsp:include page="/pages/fragments/header.jsp"/>
</div>
<div class="genre_body_wrap">
    <div class="left_bar_fragment">
        <jsp:include page="/pages/fragments/leftbar.jsp"/>
    </div>
    <div class="container">
        <input id="genres_id_search" type="text" name="search_genres" autocomplete="off"
               placeholder="<fmt:message key="search"/>">
        <table id="genres-table">
            <thead>
            <tr>
                <th class="center"><fmt:message key="genres.name"/></th>
                <th class="center"><fmt:message key="genres.operations"/></th>
            </tr>
            </thead>
            <tbody id="books-body" class="searchable">
            <%--@elvariable id="genres" type="java"--%>
            <c:forEach items="${genres}" var="genre">
                <tr id="${genre.id}">
                    <td class="center">${genre.name}</td>
                    <td class="operations center">
                        <form method="POST" class="delete-genre-form-${genre.id}"
                              action="${pageContext.request.contextPath}/mainMenu?action=DeleteGenre&id=${genre.id}">
                            <c:choose>
                                <c:when test="${fn:length(genre.books)>0}">
                                    <button type="submit" class="btn disabled"><i
                                            class="material-icons">delete</i>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button id="confirmGenreDel-${genre.id}" type="submit"
                                            class="btn red"
                                            onclick="deleteGenre(${genre.id})"><i
                                            class="material-icons">delete</i>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                        <a class="btn blue modal-trigger" href="#updateGenre"
                           onclick="passDataToUpdateModal('${genre.id}', '${genre.name}')">
                            <i class="large material-icons">mode_edit</i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="fixed-action-btn">
            <a class="btn-floating btn-large red modal-trigger" href="#createGenre">
                <i class="large material-icons">mode_edit</i>
            </a>
        </div>
        <!-- Confirmation delete modal -->
        <div id="modal5" class="modal">
            <div class="modal-content">
                <h4><fmt:message key="genres.delete.confirmation"/></h4>
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

        <!-- Create Genre Modal -->
        <div id="createGenre" class="modal">
            <div class="modal-content">
                <h4><fmt:message key="genres.create.modal.title"/></h4>
                <form action="${pageContext.request.contextPath}/mainMenu?action=SaveGenre"
                      class="submitGenre" method="post">
                    <input id="genre-name" type="text" name="genreName"
                           placeholder="<fmt:message key="genres.placeholder"/>">
                    <input id="submitGenre" type="submit" class="btn"
                           value="<fmt:message key="leftbar.create_book.modal.submit"/>"/>
                </form>
            </div>
        </div>

        <!-- Update Genre Modal -->
        <div id="updateGenre" class="modal">
            <div class="modal-content">
                <h4><fmt:message key="genres.update.modal.title"/></h4>
                <form action="${pageContext.request.contextPath}/mainMenu?action=UpdateGenre"
                      class="updateGenreSubmit"
                      method="post">
                    <input id="genre-update-id" type="hidden" name="id">
                    <input id="genre-update-name" type="text" name="genreName"
                           placeholder="<fmt:message key="genres.placeholder"/>">
                    <input id="updateGenreSubmit" type="submit" class="btn"
                           value="<fmt:message key="leftbar.create_book.modal.submit"/>"/>
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

  function deleteGenre(id) {
    event.preventDefault();
    $('#modal5').modal('open');

    $("#confirm-delete-modal").click(function () {
      $('.delete-genre-form-' + id).submit();
    });
  }

  function passDataToUpdateModal(id, name) {
    document.getElementById('genre-update-id').value = id;
    document.getElementById('genre-update-name').value = name;
  }
</script>
</body>
<jsp:include page="/pages/fragments/modalCreateBook.jsp"/>
</html>
