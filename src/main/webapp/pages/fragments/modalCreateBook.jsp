<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="localization"/>
<!-- Create Book Modal -->
<div id="modal1" class="modal">
    <div class="modal-content">
        <div class="header_create_book">
            <h4 class="title_create_book"><fmt:message key="leftbar.create_book.modal.title"/></h4>
            <a class="modal-close close_create_book" href="#">
                <i class="material-icons logo_tab">close</i>
            </a>
        </div>
        <form id="createBook" method="POST" class="bookForm"
              action="${pageContext.request.contextPath}/mainMenu?action=SaveBook"
              enctype="multipart/form-data">
            <input id="create-book-title" type="text" name="title" placeholder="Book title">
            <select name="genreId">
                <option id="create-book-genre" value="" disabled selected><fmt:message
                        key="leftbar.create_book.modal.chose_genre"/></option>
                <%--@elvariable id="genres" type="java"--%>
                <c:forEach items="${genres}" var="genre">
                    <option value="${genre.id}">${genre.name}</option>
                </c:forEach>
            </select>
            <select name="authorId">
                <option id="create-book-author" value="" disabled selected><fmt:message
                        key="leftbar.create_book.modal.chose_author"/></option>
                <%--@elvariable id="authors" type="java"--%>
                <c:forEach items="${authors}" var="author">
                    <option class="option_author_modal"
                            value="${author.id}">${author.firstName} ${author.lastName}</option>
                </c:forEach>
            </select>
            <input id="create-book-date" type="text" class="datepicker" name="dateOfPublication"
                   placeholder="<fmt:message key="leftbar.create_book.modal.date"/>">
            <input id="create-book-publH" type="text" name="bookPublishingHouse"
                   placeholder="<fmt:message key="leftbar.create_book.modal.house"/>">
            <input id="create-book-count" type="text" name="countInStock"
                   placeholder="<fmt:message key="leftbar.create_book.modal.count"/>">
            <div class="file-field input-field">
                <div class="btn">
                    <span>Image</span>
                    <input type="file" name="file">
                </div>
                <div class="file-path-wrapper">
                    <input class="file-path validate" type="text" placeholder="Upload image">
                </div>
            </div>
            <div class="footer_create_book">
                <button id="submitBook" type="submit" class="btn"><fmt:message
                        key="leftbar.create_book.modal.submit"/></button>
            </div>
        </form>
    </div>
    <script>
      let maxDate = new Date();
      $('#create-book-date').datepicker({
            container: 'body',
            format: "yyyy-mm-dd",
            maxDate: maxDate
          }
      );
    </script>
</div>
