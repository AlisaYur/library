<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="localization"/>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="signin.title"/></title>
    <meta name="viewport" content="initial-scale=1.0, width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"
          type="text/css"/>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/auth/validation.js"></script>
</head>
<body class="signin_page">

<div class="admin_block">
    <div class="admin_info">
        <div class="user_photo">
        </div>
        <p><fmt:message key="signin.header.name"/></p>
    </div>
    <div class="container">
        <div class="row">
            <div class="form_block">
                <form class="col s12 offset submitForm" method="post"
                      action="${pageContext.request.contextPath}/mainMenu?action=SignIn">
                    <div class="row">
                        <div class="input-field col s12 block_username">
                            <i class="material-icons prefix pt-5">person_outline</i>
                            <input name="username" id="username" type="text" class="validate"
                                   autofocus>
                            <label class="label_username" for="username"><fmt:message
                                    key="signin.field.username"/></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 block_password">
                            <i class="material-icons prefix pt-5">lock_outline</i>
                            <input name="password" id="password" type="password" class="validate">
                            <label class="label_password" for="password"><fmt:message
                                    key="signin.field.password"/></label>
                        </div>
                    </div>
                    <div class="row remember_me_block">
                        <div class="col s12 m12 l12 ml-2 mt-3">
                            <label>
                                <input type="checkbox" name="remember-me"/>
                                <span><fmt:message key="signin.field.remember"/></span>
                            </label>
                        </div>
                    </div>
                    <div class="row">
                        <p class="center-align">
                            <button id="submitButton" class="btn waves-effect waves-light form_btn"
                                    type="submit"
                                    name="action"><fmt:message key="signin.button.signin"/>
                            </button>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="forgot_password">
        <p><a href="${pageContext.request.contextPath}/mainMenu?action=SignUpPage"><fmt:message
                key="signin.link.signup"/></a></p>
    </div>
</div>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bin/materialize.min.js"></script>
</body>
</html>
