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
      $('#manager_id_search').keyup(function () {

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
                        key="cabinet.role"/></span></a>
                <div class="operations-list">
                    <button class="btn waves-effect waves-light btn_oper modal-trigger"
                            data-target="change_name_lib"
                            type="submit" name="action"><fmt:message
                            key="cabinet.button.login_change"/>
                        <i class="material-icons right">edit</i>
                    </button>
                    <button class="btn waves-effect waves-light btn_oper modal-trigger"
                            data-target="change_email_lib"
                            type="submit" name="action"><fmt:message
                            key="cabinet.button.email_change"/>
                        <i class="material-icons right">edit</i>
                    </button>
                    <button class="btn waves-effect waves-light btn_oper modal-trigger"
                            data-target="change_pass_lib"
                            type="submit" name="action"><span class="change_pass"><fmt:message
                            key="cabinet.button.password_change"/></span>
                        <i class="material-icons right">edit</i>
                    </button>
                    <button class="btn waves-effect waves-light btn_log_out"
                            type="submit" name="action">
                        <a class="log_out_link"
                           href="${pageContext.request.contextPath}/mainMenu?action=ManagerCabinet">
                            <span class="log_out_span">Active Orders</span>
                            <i class="material-icons log_out_icon">assignment_return</i>
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
        <input type="text" id="manager_id_search" name="cab_manager_search"
               class="cab_reader_search"
               placeholder="Input book name or reader name">
        <table class="orders_table">
            <thead>
            <tr>
                <th class="center head_orders_table"><fmt:message
                        key="cabinet.order.book_name"/></th>
                <th class="center head_orders_table"><fmt:message
                        key="cabinet.order.type_issue"/></th>
                <th class="center head_orders_table"><fmt:message
                        key="cabinet.order.start_date"/></th>
                <th class="center head_orders_table"><fmt:message
                        key="cabinet.order.delivery"/></th>
                <th class="center head_orders_table"><fmt:message key="cabinet.order.reader"/></th>
                <th class="center head_orders_table"><fmt:message key="cabinet.order.status"/></th>
            </tr>
            </thead>
            <tbody id="order_tbody" class="searchable">
            <jsp:useBean id="orders" scope="request" type="java.util.List"/>
            <c:forEach items="${orders}" var="order">
                <c:if test="${order.status eq 'CLOSE'}">
                    <tr id="table_row_manager_${order.id}"
                        onclick="moreReaderInfo('${order.userLogin.name}', '${order.userLogin.login}', '${order.userLogin.email}', 'table_row_manager_' + '${order.id}', '${order.penalty}', '${order.id}', '${order.status}');">
                        <td id="${order.id}" hidden>Id</td>
                        <td class="center">${order.bookId.title}</td>
                        <td class="center">${order.typeIssue}</td>
                        <td class="center">${order.startDate}</td>
                        <td class="center">${order.dateOfDelivery}</td>
                        <td class="center">
                            <a href="#"
                               onclick="moreReaderInfo('${order.userLogin.name}', '${order.userLogin.login}', '${order.userLogin.email}', 'table_row_manager_' + '${order.id}', '${order.penalty}', '${order.id}', '${order.status}');">
                                    ${order.userLogin.login}</a>
                        </td>
                        <td id="order_status_${order.id}" class="center">
                            <c:choose>
                                <c:when test="${order.status eq 'NEW'}">
                                    <div class="dot-green tooltipped" data-position="bottom"
                                         data-tooltip="New"></div>
                                </c:when>
                                <c:when test="${order.status eq 'APPROVED'}">
                                    <div class="dot-yellow tooltipped" data-position="bottom"
                                         data-tooltip="Approved"></div>
                                </c:when>
                                <c:when test="${order.status eq 'PENALTY'}">
                                    <div class="dot-red tooltipped" data-position="bottom"
                                         data-tooltip="Penalty"></div>
                                </c:when>
                                <c:when test="${order.status eq 'CLOSE'}">
                                    <div class="dot-grey tooltipped" data-position="bottom"
                                         data-tooltip="Closed"></div>
                                </c:when>
                                <c:when test="${order.status eq 'RETURNS'}">
                                    <div class="dot-blue tooltipped" data-position="bottom"
                                         data-tooltip="Returns"></div>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div id="select_order_block">
        <h5>Select Order</h5>
    </div>
    <div id="info_order_block">
        <div class="order_info">
            <h5>Info of Order</h5>
            <div class="reader_info">
                <p class="reader_username">Username</p>
                <p class="reader_login">Login</p>
                <p class="reader_email">Email</p>
                <p id="reader_count_all_orders">All Orders: 10</p>
                <p id="reader_count_active_orders">Active Orders: 10</p>
                <p id="reader_count_penalty">Penalty Orders: 0</p>
            </div>
            <p id="order_penalty_info" class="center">Order Penalty: ($)</p>
            <div class="center current_status_manager"></div>
            <a class="waves-effect waves-light btn modal-trigger" href="#send_mail"><i
                    class="material-icons">email</i></a>
        </div>
    </div>

    <!-- Send Reader Email Modal Structure -->
    <div id="send_mail" class="modal">
        <div class="modal-content header_mail">
            <h4>Send Email</h4>
        </div>
        <div class="modal-content model_email_body">
            <form action="${pageContext.request.contextPath}/mainMenu?action=SendEmail"
                  method="post" class="form_main_wrapper">
                <div class="form-group">
                    <label for="reader_email_readonly">Reader Email</label>
                    <input type="email" id="reader_email_readonly" name="email" readonly>
                    <label for="title_message">Title</label>
                    <input type="text" id="title_message" placeholder="Title" name="title" required>
                    <label for="ta_message">
                        <textarea class="textarea_message" name="message" id="ta_message" cols="30"
                                  rows="40" required></textarea>
                    </label>
                </div>
                <button type="submit" class="btn btn-primary btn_send">Send</button>
            </form>
        </div>
    </div>

    <!-- Change Name Modal Structure -->
    <div id="change_name_lib" class="modal">
        <div class="modal-content">
            <h4><fmt:message key="cabinet.button.login_change"/></h4>
            <label for="name_modal_edit_librarian">
                <fmt:message key="cabinet.modal.change_login.new"/>
                <input id="name_modal_edit_librarian" name="name_modal_edit_librarian" type="text"
                       class="validate"
                       value="${sessionScope.user.login}">
            </label>
        </div>
        <div class="modal-footer">
            <a href="#" class="modal-close waves-effect waves-red btn red"><fmt:message
                    key="cancel"/></a>
            <a href="#" onclick="changeManagerLogin()"
               class="waves-effect waves-green btn"><fmt:message key="ok"/></a>
        </div>
    </div>

    <!-- Change Email Modal Structure -->
    <div id="change_email_lib" class="modal">
        <div class="modal-content">
            <h4><fmt:message key="cabinet.modal.change_email.title"/></h4>
            <label for="email_modal_edit_librarian">
                <fmt:message key="cabinet.modal.change_email.new"/>
                <input id="email_modal_edit_librarian" name="email_modal_edit_librarian"
                       type="email" class="validate"
                       value="${sessionScope.user.email}">
            </label>
        </div>
        <div class="modal-footer">
            <a href="#" class="modal-close waves-effect waves-red btn red"><fmt:message
                    key="cancel"/></a>
            <a href="#" onclick="changeManagerEmail()"
               class="waves-effect waves-green btn"><fmt:message key="ok"/></a>
        </div>
    </div>

    <!-- Change Pass Modal Structure -->
    <div id="change_pass_lib" class="modal">
        <div class="modal-content">
            <h4><fmt:message key="cabinet.modal.change_password"/></h4>
            <label for="old_pass_modal_edit_librarian">
                <fmt:message key="cabinet.modal.change_password.old"/>
                <input id="old_pass_modal_edit_librarian" name="old_pass_modal_edit_librarian"
                       type="password">
            </label>
            <label for="pass_modal_edit_librarian">
                <fmt:message key="cabinet.modal.change_password.new"/>
                <input id="pass_modal_edit_librarian" name="pass_modal_edit_librarian"
                       type="password">
            </label>
        </div>
        <div class="modal-footer">
            <a href="#" class="modal-close waves-effect waves-red btn red"><fmt:message
                    key="cancel"/></a>
            <a href="#" onclick="changeManagerPass()"
               class="waves-effect waves-green btn"><fmt:message key="ok"/></a>
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
               class="modal-close waves-effect waves-red btn red"><fmt:message key="yes"/></a>
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
        src="${pageContext.request.contextPath}/static/js/manager/changeManagerLogin.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/manager/changeManagerEmail.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/manager/changeManagerPass.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/order/changeStatus.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/manager/readerInfo.js"></script>
<script>
  $(document).ready(function () {
    $('.modal').modal();
    $('.tooltipped').tooltip();
  });
</script>
</html>
