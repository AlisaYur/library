<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="localization"/>
<%@ taglib uri="/WEB-INF/error.tld" prefix="e" %>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="books.title"/></title>
    <meta name="viewport" content="initial-scale=1.0, width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"
          type="text/css"/>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/book/validation.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/book/sortBooks.js"></script>
</head>
<body>
<div class="container all-books-container">
    <div class="soring_wrapper">
        <h5 class="title_sorting"><fmt:message key="books.sorted_by"/></h5>
        <p class="sort_select"><a id="title-sort" href="#"><fmt:message
                key="books.table.header.title"/><i
                id="title-arrow"
                class="material-icons tiny">arrow_drop_up</i></a>
        </p>
        <p class="sort_select"><a id="author-sort" href="#"><fmt:message
                key="books.table.header.author"/><i
                id="author-arrow"
                class="material-icons tiny">arrow_drop_up</i></a>
        </p>
        <p class="sort_select"><a id="genre-sort" href="#"><fmt:message
                key="books.table.header.genre"/><i
                id="genre-arrow"
                class="material-icons tiny">arrow_drop_up</i></a>
        </p>
        <p class="sort_select"><a id="house-sort" href="#"><fmt:message
                key="books.table.header.publishing_house"/><i
                id="house-arrow"
                class="material-icons tiny">arrow_drop_up</i></a>
        </p>
        <p class="sort_select"><a id="date-sort" href="#"><fmt:message
                key="books.table.header.publish_date"/><i
                id="date-arrow"
                class="material-icons tiny">arrow_drop_up</i></a>
        <p class="sort_select"><a id="count-sort" href="#"><fmt:message
                key="books.table.header.count"/><i
                id="count-arrow"
                class="material-icons tiny">arrow_drop_up</i></a>
    </div>
    <div id="filter_tag"></div>
    <c:if test="${empty books}">
        <p class="center_page"><fmt:message key="books.nothing_found"/></p>
    </c:if>
    <div id="grid_books" class="grid_wrapper">
        <%--@elvariable id="books" type="java"--%>
        <c:forEach items="${books}" var="book">
        <c:if test="${book.countInStock <= 0 or sessionScope.readerIsPenalty eq true}">
        <div id="${book.id}" class="book_element disabled">
            </c:if>
            <c:if test="${book.countInStock > 0 and empty sessionScope.readerIsPenalty}">
            <div id="${book.id}" class="book_element">
                </c:if>
                <c:choose>
                    <c:when test="${fn:length(book.image) > 0}">
                        <img class="image_book" src="${book.encodedImage}" alt="BookImage"
                             width="245px" height="380px">
                    </c:when>
                    <c:otherwise>
                        <img class="image_book"
                             src="${pageContext.request.contextPath}/static/images/liabrary-booknotfound.png"
                             alt="BookImage" width="245px" height="380px">
                    </c:otherwise>
                </c:choose>
                <div class="info_book_grid">
                    <h5 class="center title">${book.title}</h5>
                </div>
                <c:choose>
                    <c:when test="${sessionScope.roles.name eq 'ADMIN_ROLE'}">
                        <div class="admin_panel_book">
                            <p class="center caption_book_author">${book.author.firstName} ${book.author.lastName}</p>
                            <div class="center elements_panel">
                                <form method="POST"
                                      action="${pageContext.request.contextPath}/books/delete?id=${book.id}">
                                    <button type="submit" class="delete_book_btn"><i
                                            class="material-icons red-text">delete</i>
                                    </button>
                                </form>
                                <a href="#modal2" class="modal-trigger updateModal"
                                   onclick="passData('${book.id}',
                                           '${book.title}',
                                           '${book.genre.id}',
                                           '${book.author.id}',
                                           '${book.publishingHouse}',
                                           '${book.dateOfPublication}',
                                           '${book.countInStock}')"><i
                                        class="material-icons">edit</i>
                                </a>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.roles.name eq 'READER_ROLE'}">
                        <div class="payment_block">
                            <div class="center book_btn">
                                <c:choose>
                                    <c:when test="${sessionScope.readerIsPenalty eq true}">
                                        <a href="#"
                                           class="modal-trigger link_buy_book disabled_buy_link tooltipped"
                                           data-position="bottom" data-tooltip="We have a penalty!">
                                            <i class="material-icons buy_icon">shopping_basket</i>
                                        </a>
                                    </c:when>
                                    <c:when test="${book.countInStock <= 0}">
                                        <a href="#"
                                           class="modal-trigger link_buy_book disabled_buy_link tooltipped"
                                           data-position="bottom"
                                           data-tooltip="Sorry! No books available">
                                            <i class="material-icons buy_icon">shopping_basket</i>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="#createOrder" class="modal-trigger link_buy_book"
                                           onclick="createOrder('${book.id}', '${book.title}')">
                                            <i class="material-icons buy_icon">shopping_basket</i>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <p class="center caption_book_author">${book.author.firstName} ${book.author.lastName}</p>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.roles.name eq 'LIBRARIAN_ROLE'}">
                        <div class="payment_block">
                            <p class="center caption_book_author">${book.author.firstName} ${book.author.lastName}</p>
                        </div>
                    </c:when>
                </c:choose>
                <c:if test="${empty sessionScope.roles}">
                    <div class="payment_block">
                        <div class="center book_btn">
                            <c:if test="${book.countInStock <= 0}">
                                <a href="${pageContext.request.contextPath}/mainMenu?action=AuthPage"
                                   class="link_buy_book disabled_buy_link">
                                    <i class="material-icons buy_icon">shopping_basket</i>
                                </a>
                            </c:if>
                            <c:if test="${book.countInStock > 0}">
                                <a href="${pageContext.request.contextPath}/mainMenu?action=AuthPage"
                                   class="link_buy_book">
                                    <i class="material-icons buy_icon">shopping_basket</i>
                                </a>
                            </c:if>
                        </div>
                        <p class="center caption_book_author">${book.author.firstName} ${book.author.lastName}</p>
                    </div>
                </c:if>
                <div class="book_footer">
                    <div class="footer_caption">
                        <p class="center caption_book"><span
                                class="footer_category"><fmt:message
                                key="books.card.genre"/></span>${book.genre.name}
                        </p>
                        <p class="center caption_book"><span
                                class="footer_category"><fmt:message
                                key="books.card.publish_house"/></span>${book.publishingHouse}</p>
                        <p class="center caption_book"><span
                                class="footer_category"><fmt:message
                                key="books.card.publ_date"/></span>${book.dateOfPublication}
                        </p>
                        <p class="center caption_book"><span
                                class="footer_category"><fmt:message
                                key="books.card.count_avail"/></span>${book.countInStock}</p>
                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>
    <!-- Update Book Modal -->
    <div id="modal2" class="modal">
        <div class="modal-content">
            <h4><fmt:message key="books.update.modal.title"/></h4>
            <form id="update" method="POST" class="bookFormUpdate"
                  action="${pageContext.request.contextPath}/mainMenu?action=UpdateBook"
                  enctype="multipart/form-data">
                <input id="update-id" type="hidden" name="id">
                <input id="update-title" type="text" name="title"
                       placeholder="<fmt:message key="books.update.modal.input_name"/>">
                <select name="genreId">
                    <%--@elvariable id="genres" type="java"--%>
                    <c:forEach items="${genres}" var="genre">
                        <option id="update-genre-${genre.id}"
                                value="${genre.id}">${genre.name}</option>
                    </c:forEach>
                </select>
                <select name="authorId">
                    <%--@elvariable id="authors" type="java"--%>
                    <c:forEach items="${authors}" var="author">
                        <option id="update-author-${author.id}"
                                value="${author.id}">${author.firstName} ${author.lastName}</option>
                    </c:forEach>
                </select>
                <input id="update-date" type="text" name="dateOfPublication" class="datepicker"
                       placeholder="<fmt:message key="books.update.modal.input_date"/>">
                <input id="update-publ-house" type="text" name="bookPublishingHouse"
                       placeholder="<fmt:message key="books.update.modal.input_house"/>">
                <input id="update-count" type="text" name="countInStock"
                       placeholder="<fmt:message key="books.update.modal.input_count"/>">
                <div class="file-field input-field">
                    <div class="btn">
                        <span>Image</span>
                        <input type="file" name="file">
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text" placeholder="Upload image">
                    </div>
                </div>
                <button id="updateBook" type="submit" class="btn"><fmt:message
                        key="books.update.modal.button"/></button>
            </form>
        </div>
    </div>

    <!-- Create Order Modal -->
    <div id="createOrder" class="modal">
        <div class="modal-content">
            <h4 class="center"><fmt:message key="books.create_order.modal.title"/></h4>
            <a class="modal-close close_create_book" href="#">
                <i class="material-icons logo_tab">close</i>
            </a>
            <form action="${pageContext.request.contextPath}/mainMenu?action=CreateOrder"
                  method="post">
                <input id="book-id-order" class="center" type="hidden" name="id">
                <label for="book_name" class="center input_book_name">
                    <span class="center"><fmt:message key="books.create_order.modal.name"/></span>
                    <%--@elvariable id="bookName" type="java"--%>
                    <input type="text" class="center" id="book_name" name="book_name"
                           value="${bookName}"
                           readonly="readonly">
                </label>
                <label for="select_type_issue" class="center select_type_issue_label">
                    <fmt:message key="books.create_order.modal.issue"/>
                    <select id="select_type_issue" class="center" name="select_type_issue">
                        <option value="SUBSCRIPTION"><fmt:message
                                key="books.create_order.modal.subscription"/></option>
                        <option value="READING_ROOM"><fmt:message
                                key="books.create_order.modal.read_room"/></option>
                    </select>
                </label>
                <div class="dates_create_order center">
                    <label for="currentDate" class="center">
                        <fmt:message key="books.create_order.modal.start_date"/>
                        <%--@elvariable id="currentDate" type="java"--%>
                        <input id="currentDate" class="center" type="text" name="currentDate"
                               value="${currentDate}" readonly="readonly">
                    </label>
                    <label for="dateOfDelivery">
                        <fmt:message key="books.create_order.modal.back_date"/>
                        <input id="dateOfDelivery" type="text" class="datepicker center"
                               name="dateOfDelivery" required>
                    </label>
                </div>
                <div class="btn_submit center">
                    <input type="submit" class="btn center" name="btn_create_order"
                           value="<fmt:message key="books.create_order.modal.button"/>"/>
                </div>
            </form>
        </div>
    </div>
</div>
<e:error/>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/error/showErrors.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bin/materialize.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/book/clickEvents.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/book/removeChipCheckbox.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/book/passDataToUpdateModal.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/order/passDataToCreateOrder.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/controlblock/saveCheckBoxStatus.js"></script>
<script>
  $(document).ready(function () {
    $('.modal').modal();
    $('select').formSelect();
    $('.dropdown-trigger').dropdown();
  });

  let date = new Date();
  $('#dateOfDelivery').datepicker({
        container: 'body',
        format: "yyyy-mm-dd",
        minDate: date
      }
  );

  $('#update-date').datepicker({
    container: 'body',
    format: "yyyy-mm-dd"
  });

  $(document).ready(function () {
    $('.tooltipped').tooltip();
  });
</script>
</body>
</html>
