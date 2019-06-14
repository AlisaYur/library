<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="localization"/>
<div class="leftBar">
    <nav>
        <div class="nav-wrapper">
            <ul class="nav-mobile">
                <li class="tab nav_element_ref_book">
                    <a class="waves-effect waves-block waves-light link_ref_book tooltipped"
                       data-position="right"
                       data-tooltip="<fmt:message key="leftbar.librarian"/>"
                       href="${pageContext.request.contextPath}/mainMenu?action=Managers">
                        <i class="material-icons logo_tab">local_library</i>
                    </a>
                </li>
                <li class="tab nav_element_ref_book">
                    <a class="waves-effect waves-block waves-light link_ref_book tooltipped"
                       data-position="right"
                       data-tooltip="<fmt:message key="leftbar.readers"/>"
                       href="${pageContext.request.contextPath}/mainMenu?action=Readers">
                        <i class="material-icons logo_tab">person</i>
                    </a>
                </li>
                <li class="tab nav_element_ref_book">
                    <a class="waves-effect waves-block waves-light link_ref_book tooltipped"
                       data-position="right"
                       data-tooltip="<fmt:message key="leftbar.authors"/>"
                       href="${pageContext.request.contextPath}/mainMenu?action=Authors">
                        <i class="material-icons logo_tab">format_quote</i>
                    </a>
                </li>
                <li class="tab nav_element_ref_book">
                    <a class="waves-effect waves-block waves-light link_ref_book tooltipped"
                       data-position="right"
                       data-tooltip="<fmt:message key="leftbar.genres"/>"
                       href="${pageContext.request.contextPath}/mainMenu?action=Genres">
                        <i class="material-icons logo_tab">local_activity</i>
                    </a>
                </li>
                <li class="tab nav_element_ref_book">
                    <a class="waves-effect waves-block waves-light link_ref_book tooltipped modal-trigger"
                       data-position="right"
                       data-tooltip="<fmt:message key="leftbar.create_book"/>" href="#modal1">
                        <i class="material-icons logo_tab">library_add</i>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/leftbar/getUsernameAndEmail.js"></script>
    <script>
      $(document).ready(function () {
        $('.modal').modal();
        M.updateTextFields();
      });
    </script>
</div>