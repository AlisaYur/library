<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:choose>
    <c:when test="${cookie.get('language').value == 'ru'}">
        <fmt:setLocale value='ru' scope="session"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value='en' scope="session"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="localization"/>
<div class="header_page">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"
          type="text/css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/header/headerSearch.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/header/tooltips.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/header/showCountActiveOrders.js"></script>
    <nav class="nav-extended navbar-color gradient-45deg-light-blue-cyan fixed">
        <div class="nav-wrapper">
            <a href="${pageContext.request.contextPath}/mainMenu" class="brand-logo">
                <span class="logo"></span>
            </a>
            <span class="text-logo"><fmt:message key="header.main"/></span>
            <ul class="nav_content right hide-on-med-and-down">
                <li class="nav_search">
                    <div class="inner_search-content">
                        <form action="${pageContext.request.contextPath}/mainMenu?action=SearchBook"
                              method="POST"
                              class="inner_search-content">
                            <label for="Search" class="search_icon">
                                <c:choose>
                                    <%--@elvariable id="searchParam" type="java"--%>
                                    <c:when test="${not empty searchParam}">
                                        <script>
                                          $(document).ready(function () {
                                            $('.input_search').val('${searchParam}');
                                          })
                                        </script>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="material-icons">search</i><fmt:message
                                            key="header.search"/>
                                    </c:otherwise>
                                </c:choose>
                            </label>
                            <input type="search" class="input_search" id="Search"
                                   name="search_book">
                            <input type="submit" name="btn_search" hidden/>
                        </form>
                    </div>
                </li>
                <!-- Dropdown Language -->
                <a class='dropdown-trigger' href='#' data-target='dropdown_lang'> <i
                        class="material-icons">language</i></a>
                <c:if test="${sessionScope.roles.name eq 'READER_ROLE'}">
                    <li class="shoping_basket_menu">
                        <a class="waves-effect waves-block waves-light link_ref_book
                    tooltipped shoping_basket_menu_status"
                           data-position="left"
                           data-tooltip="<fmt:message key="header.tooltip.basket"/>"
                           href="${pageContext.request.contextPath}/mainMenu?action=ReaderCabinet">
                            <i class="material-icons logo_tab">shopping_basket</i>
                            <small id="basket_count_order"
                                   class="notification-badge shoping_basket_notifications">0
                            </small>
                        </a>
                    </li>
                </c:if>
                <!-- Dropdown User Trigger -->
                <li class="user_dropdown">
                    <a class="dropdown-trigger waves-effect waves-block waves-light profile-button username_text"
                       href="#"
                       data-target="dropdown1">
                        <span class="avatar-status avatar-online">
                            <div class="user-photo"></div>
                        </span>
                    </a>
                    <ul id="dropdown1" class="dropdown-content dropdown-content_username">
                        <c:choose>
                            <c:when test="${sessionScope.roles.name eq 'READER_ROLE'}">
                                <li class="settings"><a
                                        href="${pageContext.request.contextPath}/mainMenu?action=ReaderCabinet">
                                    <i class="material-icons">person</i><fmt:message
                                        key="header.cabinet"/>
                                </a></li>
                                <li class="divider"></li>
                                <li class="LogOut"><a class="modal-trigger" href="#log_out_modal">
                                    <i class="material-icons">keyboard_tab</i>
                                    <fmt:message key="header.logout"/>
                                </a></li>
                            </c:when>
                            <c:when test="${sessionScope.roles.name eq 'LIBRARIAN_ROLE'}">
                                <li class="settings"><a
                                        href="${pageContext.request.contextPath}/mainMenu?action=ManagerCabinet">
                                    <i class="material-icons">person</i><fmt:message
                                        key="header.cabinet"/>
                                </a></li>
                                <li class="divider"></li>
                                <li class="LogOut"><a class="modal-trigger" href="#log_out_modal">
                                    <i class="material-icons">keyboard_tab</i>
                                    <fmt:message key="header.logout"/>
                                </a></li>
                            </c:when>
                            <c:when test="${sessionScope.roles.name eq 'ADMIN_ROLE'}">
                                <li class="LogOut"><a class="modal-trigger" href="#log_out_modal">
                                    <i class="material-icons">keyboard_tab</i>
                                    <fmt:message key="header.logout"/>
                                </a></li>
                            </c:when>
                        </c:choose>
                        <c:if test="${empty sessionScope.roles}">
                            <li class="settings"><a
                                    href="${pageContext.request.contextPath}/mainMenu?action=AuthPage">
                                <i class="material-icons">person</i><fmt:message
                                    key="header.signin"/>
                            </a></li>
                            <li class="divider"></li>
                            <li class="settings"><a
                                    href="${pageContext.request.contextPath}/mainMenu?action=SignUpPage">
                                <i class="material-icons">person_add</i><fmt:message
                                    key="header.signup"/>
                            </a></li>
                        </c:if>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <!-- Logout modal -->
    <div id="log_out_modal" class="modal">
        <div class="modal-content">
            <h4 class="center title"><fmt:message key="header.logout_modal.header"/></h4>
            <p class="center"><fmt:message key="header.logout_modal.text"/></p>
        </div>
        <div class="modal-footer">
            <a href="#" class="modal-close waves-effect waves-green btn"><fmt:message
                    key="cancel"/></a>
            <a href="${pageContext.request.contextPath}/mainMenu?action=LogOut"
               class="modal-close waves-effect waves-red btn red"><fmt:message
                    key="yes"/></a>
        </div>
    </div>
    <script>
      $(document).ready(function () {
        $(".dropdown-trigger").dropdown();
        $('select').formSelect();
        $('.modal').modal();
        M.updateTextFields();

        $("#en").click(function () {
          document.cookie = "language=en";
          location.reload();

        });
        $("#ru").click(function () {
          document.cookie = "language=ru";
          location.reload();
        });
      });

      countOrders('${sessionScope.user.login}');
    </script>
</div>

<!-- Dropdown Language -->
<ul id='dropdown_lang' class='dropdown-content'>
    <li id="en" class="en"></li>
    <li id="ru" class="ru"></li>
</ul>
