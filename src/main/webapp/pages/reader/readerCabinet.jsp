<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/error.tld" prefix="e" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="localization"/>
<html>
<head>
    <title><fmt:message key="cabinet.title"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"
          type="text/css"/>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
<script>
  $(document).ready(function () {
    (function ($) {
      $('#cab_search_reader').keyup(function () {

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
<div class="reader-cabinet-wrapper books-table">
    <div class="reader-info">
        <div class="reader-head-cab">
            <div class="user-view">
                <div class="background"></div>
            </div>
        </div>
        <div class="reader-side-content-cab">
            <div class="head_side">
                <a href="#"><span id="name_left_bar" class="white-text name">John Doe</span></a>
                <a href="#"><span id="email_left_bar" class="white-text email">
                                    jdandturk@gmail.com</span></a>
                <a href="#"><span class="white-text name"><fmt:message
                        key="cabinet.reader.reader"/></span></a>
                <div class="operations-list">
                    <button class="btn waves-effect waves-light btn_oper modal-trigger"
                            data-target="change_name"
                            type="submit" name="action"><fmt:message
                            key="cabinet.button.login_change"/>
                        <i class="material-icons right">edit</i>
                    </button>
                    <button class="btn waves-effect waves-light btn_oper modal-trigger"
                            data-target="change_email"
                            type="submit" name="action"><fmt:message
                            key="cabinet.button.email_change"/>
                        <i class="material-icons right">edit</i>
                    </button>
                    <button class="btn waves-effect waves-light btn_oper modal-trigger"
                            data-target="change_pass"
                            type="submit" name="action"><span class="change_pass"><fmt:message
                            key="cabinet.button.password_change"/></span>
                        <i class="material-icons right">edit</i>
                    </button>
                    <button class="btn waves-effect waves-light btn_log_out"
                            type="submit" name="action">
                        <a class="log_out_link"
                           href="${pageContext.request.contextPath}/mainMenu?action=HistoryReaderOrder">
                            <span class="log_out_span">History Orders</span>
                            <i class="material-icons log_out_icon">history</i>
                        </a>
                    </button>
                    <button class="btn waves-effect waves-light red btn_log_out modal-trigger"
                            data-target="log_out_modal" type="submit" name="action">
                        <a class="log_out_link" href="#">
                            <span class="log_out_span"><fmt:message
                                    key="cabinet.button.logout"/></span><i
                                class="material-icons log_out_icon">keyboard_tab</i>
                        </a>
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="order_block_wrapper">
        <input type="text" id="cab_search_reader"
               name="cab_reader_search" class="cab_reader_search"
               placeholder="<fmt:message key="cabinet.input.name"/>">
        <table class="orders_table">
            <thead>
            <tr>
                <th class="center"><fmt:message key="cabinet.order.book_name"/></th>
                <th class="center"><fmt:message key="cabinet.order.type_issue"/></th>
                <th class="center"><fmt:message key="cabinet.order.start_date"/></th>
                <th class="center"><fmt:message key="cabinet.order.delivery"/></th>
                <th class="center"><fmt:message key="cabinet.order.penalty"/></th>
                <th class="center"><fmt:message key="cabinet.order.status"/></th>
                <th class="center"><fmt:message key="cabinet.order.operations"/></th>
            </tr>
            </thead>
            <tbody class="searchable">
            <jsp:useBean id="orders" scope="request" type="java.util.List"/>
            <c:forEach items="${orders}" var="order">
                <c:if test="${order.status != 'CLOSE'}">
                    <tr id="table_row_reader_${order.id}">
                        <td id="${order.id}" hidden>Id</td>
                        <td class="center">${order.bookId.title}</td>
                        <td class="center">${order.typeIssue}</td>
                        <td class="center">${order.startDate}</td>
                        <td class="center">${order.dateOfDelivery}</td>
                        <td class="center current_penalty">${order.penalty}($)</td>
                        <td id="order_status_${order.id}" class="center">
                            <c:choose>
                                <c:when test="${order.status eq 'NEW'}">
                                    <div class="dot-green tooltipped" data-position="bottom"
                                         data-tooltip="New"></div>
                                </c:when>
                                <c:when test="${order.status eq 'APPROVED'}">
                                    <div class="dot-green tooltipped" data-position="bottom"
                                         data-tooltip="Approve"></div>
                                </c:when>
                                <c:when test="${order.status eq 'PENALTY'}">
                                    <div class="dot-red tooltipped" data-position="bottom"
                                         data-tooltip="Penalty"></div>
                                </c:when>
                                <c:when test="${order.status eq 'CLOSE'}">
                                    <div class="dot-grey tooltipped" data-position="bottom"
                                         data-tooltip="Close"></div>
                                </c:when>
                                <c:when test="${order.status eq 'RETURNS'}">
                                    <div class="dot-yellow tooltipped" data-position="bottom"
                                         data-tooltip="Return"></div>
                                </c:when>
                            </c:choose>
                        </td>
                        <c:if test="${order.status eq 'CLOSE'}">
                            <td class="center current_status_reader">
                                <a href="#" class="btn grey black-text disabled"><fmt:message
                                        key="cabinet.order.state.closed"/></a>
                            </td>
                        </c:if>

                        <c:if test="${order.status eq 'APPROVED'}">
                            <td id="pay_btn_${order.id}" class="center current_status_reader">
                                <a href="#"
                                   onclick="changeOrderStatus('RETURNS', ${order.id}, '#order_status_' + ${order.id}, 'table_row_reader_' + ${order.id}, 'READER_ROLE', '')"
                                   class="waves-effect waves-red btn green"><fmt:message
                                        key="cabinet.reader.order.return"/></a>
                            </td>
                        </c:if>

                        <c:if test="${order.status eq 'NEW'}">
                            <td class="center current_status_reader">
                                <a href="#"
                                   onclick="changeOrderStatus('CLOSE', ${order.id}, '#order_status_' + ${order.id}, 'table_row_reader_' + ${order.id}, 'READER_ROLE', '')"
                                   class="waves-effect waves-red btn red"><fmt:message
                                        key="cabinet.reader.order.close"/></a>
                            </td>
                        </c:if>

                        <c:if test="${order.status eq 'RETURNS'}">
                            <td class="center current_status_reader">
                                <a href="#" class="btn yellow black-text"><fmt:message
                                        key="cabinet.reader.order.wait"/></a>
                            </td>
                        </c:if>

                        <c:if test="${order.status eq 'PENALTY'}">
                            <td id="pay_btn_${order.id}" class="center current_status_reader">
                                <a href="#"
                                   onclick="createPayBtn(${order.id}, 'pay_btn_' + ${order.id})"
                                   class="waves-effect waves-red btn red"><fmt:message
                                        key="cabinet.reader.order.pay"/></a>
                            </td>
                        </c:if>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Change Name Modal Structure -->
    <div id="change_name" class="modal">
        <div class="modal-content">
            <h4><fmt:message key="cabinet.reader.change.modal.title"/></h4>
            <label for="name_modal_edit">
                <fmt:message key="cabinet.modal.change_login.new"/>
                <input id="name_modal_edit" type="text" class="validate"
                       value="${sessionScope.user.login}">
            </label>
        </div>
        <div class="modal-footer">
            <a href="#" class="modal-close waves-effect waves-red btn red"><fmt:message
                    key="cancel"/></a>
            <a href="#" onclick="changeLogin()" class="waves-effect waves-green btn"><fmt:message
                    key="yes"/></a>
        </div>
    </div>

    <!-- Change Email Modal Structure -->
    <div id="change_email" class="modal">
        <div class="modal-content">
            <h4><fmt:message key="cabinet.modal.email_change.title"/></h4>
            <label for="email_modal_edit">
                New Email
                <input id="email_modal_edit" type="email" class="validate"
                       value="${sessionScope.user.email}">
            </label>
        </div>
        <div class=" modal-footer">
            <a href="#" class="modal-close waves-effect waves-red btn red"><fmt:message
                    key="cancel"/></a>
            <a href="#" onclick="changeEmail()" class="waves-effect waves-green btn"><fmt:message
                    key="ok"/></a>
        </div>
    </div>

    <!-- Change Pass Modal Structure -->
    <div id="change_pass" class="modal">
        <div class="modal-content">
            <h4><fmt:message key="cabinet.modal.change_password"/></h4>
            <label for="old_pass_modal_edit">
                <fmt:message key="cabinet.modal.change_password.old"/>
                <input id="old_pass_modal_edit" type="password">
            </label>
            <label for="pass_modal_edit">
                <fmt:message key="cabinet.modal.change_password.new"/>
                <input id="pass_modal_edit" type="password">
            </label>
        </div>
        <div class="modal-footer">
            <a href="#" class="modal-close waves-effect waves-red btn red"><fmt:message
                    key="cancel"/></a>
            <a href="#" onclick="changePass()" class="waves-effect waves-green btn"><fmt:message
                    key="ok"/></a>
        </div>
    </div>

    <!-- Modal Structure -->
    <div id="log_out_modal" class="modal">
        <div class="modal-content">
            <h4><fmt:message key="header.logout_modal.header"/></h4>
            <p><fmt:message key="header.logout_modal.text"/></p>
        </div>
        <div class="modal-footer">
            <a href="#" class="modal-close waves-effect waves-green btn"><fmt:message
                    key="cancel"/></a>
            <a href="${pageContext.request.contextPath}/mainMenu?action=LogOut"
               class="modal-close waves-effect waves-red btn red"><fmt:message key="ok"/></a>
        </div>
    </div>
</div>
<div class="footer_fragment">
    <jsp:include page="/pages/fragments/footer.jsp"/>
</div>
<e:error/>
</body>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/error/showErrors.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bin/materialize.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/leftbar/getUsernameAndEmail.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/reader/changeLogin.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/reader/changeEmail.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/reader/changePass.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/order/changeStatus.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/payment/createPayBtn.js"></script>
<script>
  $(document).ready(function () {
    $('.modal').modal();
  });

  $('.dropdown-trigger').dropdown();
</script>
</html>
