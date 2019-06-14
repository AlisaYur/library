<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/error.tld" prefix="e" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setBundle basename="localization"/>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="signup.title"/></title>
    <meta name="viewport" content="initial-scale=1.0, width=device-width">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css"
          type="text/css"/>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/auth/validation.js"></script>
</head>
<body class="signup">
<div class="signup_block">
    <div class="page_info">
        <p>Sign Up</p>
    </div>
    <div class="container">
        <div class="row">
            <div class="reg_form_block">
                <form class="col s12 offset registrationForm" method="post"
                      action="${pageContext.request.contextPath}/mainMenu?action=SignUp">
                    <div class="row">
                        <div class="input-field col s12 reg_block_name">
                            <i class="material-icons prefix pt-5">account_circle</i>
                            <input name="name" id="name" type="text" class="validate">
                            <label class="label_name" for="name"><fmt:message
                                    key="signup.field.name"/></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 reg_block_username">
                            <i class="material-icons prefix pt-5">person_outline</i>
                            <input name="login" id="login" type="text" class="validate" autofocus>
                            <label class="label_login" for="login"><fmt:message
                                    key="signup.field.username"/></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 reg_block_password">
                            <i class="material-icons prefix pt-5">lock_outline</i>
                            <input name="password" id="password" type="password" class="validate">
                            <label class="label_password" for="password"><fmt:message
                                    key="signup.field.password"/></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 reg_block_email">
                            <i class="material-icons prefix pt-5">email</i>
                            <input name="email" id="email" type="email" class="validate">
                            <label class="label_email" for="email"><fmt:message
                                    key="signup.field.email"/></label>
                        </div>
                    </div>
                    <div class="row">
                        <p class="center-align">
                            <button id="submitRegButton"
                                    class="btn waves-effect waves-light reg-btn" type="submit"
                                    name="action"><fmt:message key="signup.button.signup"/>
                            </button>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="sign_in">
        <p><a href="${pageContext.request.contextPath}/mainMenu?action=AuthPage"><fmt:message
                key="signup.link.signin"/></a></p>
    </div>
</div>
<e:error/>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/error/showErrors.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bin/materialize.min.js"></script>
</body>
</html>
