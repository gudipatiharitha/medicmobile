<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${pageLang}" />
<fmt:setBundle basename="org.motechproject.resources.messages" var="bundle"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>MOTECH - Mobile Technology for Community Health</title>

    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap-responsive.css">
    <link rel="stylesheet" type="text/css" href="resources/css/index.css" />

    <script src="resources/lib/jquery/jquery-1.8.2.min.js" type="text/javascript"></script>
    <script src="resources/lib/jquery/jquery.form.js" type="text/javascript"></script>
    <script src="resources/lib/jquery/jquery-ui.min.js" type="text/javascript"></script>
    <script src="resources/lib/jquery/jquery.alerts.js" type="text/javascript"></script>
    <script src="resources/lib/jquery/jquery.i18n.properties-min-1.0.9.js" type="text/javascript"></script>
    <script src="resources/lib/jquery/jquery.tools.min.js" type="text/javascript"></script>
    <script src="resources/lib/jquery/jquery.blockUI.js" type="text/javascript"></script>

    <script src="resources/lib/angular/angular.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/angular-resource.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/angular-cookies.min.js" type="text/javascript"></script>
    <script src="resources/lib/angular/angular-bootstrap.js" type="text/javascript"></script>

    <script src="resources/lib/bootstrap/bootstrap.min.js"></script>

    <script src="resources/js/util.js" type="text/javascript"></script>
    <script src="resources/js/common.js" type="text/javascript"></script>
    <script src="resources/js/localization.js"></script>
    <script src="resources/js/app.js"></script>
    <script src="resources/js/controllers.js"></script>

    <script src="resources/js/dashboard.js"></script>

    <c:if test="${! empty currentModule}">
       ${currentModule.header}
    </c:if>

    <c:if test="${empty currentModule}">
        <script type="text/javascript">
            $(window).load(function() {
                initAngular();
            });
        </script>
    </c:if>
</head>
<body class="body-down" ng-controller="MasterCtrl">
<div class="bodywrap">
    <div class="header">
        <div class="container">
            <a href="."><div class="dashboard-logo"></div></a>
            <div class="nav-collapse">
                <div class="header-title"><fmt:message key="motechTitle" bundle="${bundle}"/></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>

    <div class="clearfix"></div>
    <div class="nav-collapse">
        <div class="header-nav navbar">
            <div class="navbar-inner navbar-inner-bg">
            </div>
        </div>
    </div>

    <div class="clearfix"></div>
    <div id="content" class="container">
        <div class="row-fluid">
            <div id="main-content">
                <c:if test="${loginMode=='repository'}">
                <c:if test="${empty error}">
                <div id="login" class="well2 margin-center margin-before2 spnw5">
                    <div class="box-header"><fmt:message key="security.signInUser" bundle="${bundle}"/></div>
                    <div class="box-content clearfix">
                        <div class="well3">
                            <form action="${contextPath}j_spring_security_check" method="POST" class="inside">
                                <div class="control-group">
                                    <h4><fmt:message key="security.signInWithId" bundle="${bundle}"/>&nbsp;<fmt:message key="motechId" bundle="${bundle}"/></h4>
                                </div>
                                <div class="control-group margin-before2">
                                    <input class="span12" type="text" name="j_username" placeholder="<fmt:message key="userName" bundle="${bundle}"/>"/>
                                </div>
                                <div class="control-group">
                                    <input class="span12" type="password" name="j_password" placeholder="<fmt:message key="password" bundle="${bundle}"/>"/>
                                </div>
                                <div class="control-group">
                                    <input class="btn btn-primary" value="<fmt:message key="signin" bundle="${bundle}"/>" type="submit"/>
                                    <span class="pull-right margin-before05"><a href="../../module/websecurity/api/forgot"><fmt:message key="security.signInQuestions" bundle="${bundle}"/></a></span>
                                </div>
                            </form>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
                </c:if>
                <c:if test="${error=='true'}">
                <div class="well2 margin-center margin-before2 spn9">
                    <div class="box-header"><fmt:message key="security.signInUnsuccessful" bundle="${bundle}"/></div>
                    <div class="box-content clearfix">
                        <div class="row-fluid">
                            <div class="span6 inside">
                                <div class="well3">
                                    <div class="control-group margin-before">
                                        <h4 class="login-error"><fmt:message key="wrongPassword" bundle="${bundle}"/></h4>
                                    </div>
                                    <div class="control-group">
                                        <h5 class="login-error"><fmt:message key="security.didnotRecognizeMsg" bundle="${bundle}"/></h5>
                                    </div>
                                    <div class="control-group">
                                        <h5><fmt:message key="security.donotRememberMsg1" bundle="${bundle}"/>
                                            <a href="../../module/websecurity/api/forgot"><fmt:message key="clickHere" bundle="${bundle}"/></a> <fmt:message key="security.donotRememberMsg2" bundle="${bundle}"/></h5>
                                    </div>
                                </div>
                            </div>
                            <div class="span6">
                                <div class="well3"><div class="left-divider">
                                    <form class="inside" action="${contextPath}j_spring_security_check" method="POST">
                                        <div class="control-group">
                                            <h4><fmt:message key="security.signInWithId" bundle="${bundle}"/>&nbsp;<fmt:message key="motechId" bundle="${bundle}"/></h4>
                                        </div>
                                        <div class="control-group margin-before2"">
                                            <input class="span12" type="text" name="j_username" placeholder="<fmt:message key="userName" bundle="${bundle}"/>">
                                        </div>
                                        <div class="control-group">
                                            <input class="span12" type="password" name="j_password" placeholder="<fmt:message key="password" bundle="${bundle}"/>">
                                        </div>
                                        <div class="control-group">
                                            <input class="btn btn-primary" type="submit" value="<fmt:message key="signin" bundle="${bundle}"/>"/>
                                        </div>
                                    </form>
                                </div></div>
                            </div>
                        </div>
                    </div>
                </div>
                </c:if>
                </c:if>
                <c:if test="${loginMode=='openid'}">
                    <div class="well2 margin-center margin-before2 spn6">
                        <div class="box-header"><fmt:message key="security.openIdConsumer" bundle="${bundle}"/></div>
                        <div class="box-content">
                            <div class="well3">
                                <form class="inside form-horizontal" action="${contextPath}j_spring_openid_security_check" method="POST">
                                   <div class="control-group open-id">
                                        <p>For ${openIdProviderName} users:</p>
                                        <input name="openid_identifier" type="hidden" value="${openIdProviderUrl}"/>
                                        <fmt:message key="security.signInWith" bundle="${bundle}" var="msg" />
                                        <div class="controls">
                                             <input class="btn btn-primary" type="submit" value="${msg} ${openIdProviderName}"/>
                                        </div>
                                   </div>
                                   <div class="control-group margin-before2 open-id">
                                       <p><fmt:message key="oneTimeToken" bundle="${bundle}"/>&nbsp;<a href="../../module/websecurity/api/forgotOpenId"><fmt:message key="clickHere" bundle="${bundle}"/></a></p>
                                   </div>
                                </form>
                            </div>
                            <div class="clearfix"></div>

                        </div>
                    </div>
                </c:if>

            </div>
        </div>
    </div>
</div>
</body>
</html>
