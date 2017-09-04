<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en" class="fullscreen-bg">

<head>
    <title>Login | Klorofil - Free Bootstrap Dashboard Template</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!-- VENDOR CSS -->
    <%@include file="base/web-css-base.jsp" %>
</head>

<body>
<!-- WRAPPER -->
<div id="wrapper">
    <div class="vertical-align-wrap">
        <div class="vertical-align-middle">
            <div class="auth-box ">
                <div class="left">
                    <div class="content">
                        <div class="header">
                            <div class="logo text-center"><img src="${basePath}/static/assets/img/logo-dark.png"
                                                               alt="Klorofil Logo"></div>
                            <p class="lead">Login to your account</p>
                        </div>
                        <form class="form-auth-small" action="${basePath}/login" method="post">
                            <div class="form-group">
                                <label for="username" class="control-label sr-only">用户名</label>
                                <input type="text" class="form-control" id="username" name="username" placeholder="用户名">
                            </div>
                            <div class="form-group">
                                <label for="password" class="control-label sr-only">密码</label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="密码">
                            </div>
                            <div class="form-group clearfix">
                                <label class="fancy-checkbox element-left">
                                    <input type="checkbox" name="remember-me">
                                    <span>Remember me</span>
                                </label>
                            </div>
                            <button type="submit" class="btn btn-primary btn-lg btn-block">登录</button>
                            <div class="bottom">
                                <span class="helper-text"><i class="fa fa-lock"></i> <a
                                        href="#">Forgot password?</a></span>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="right">
                    <div class="overlay"></div>
                    <div class="content text">
                        <h1 class="heading">Free Bootstrap dashboard template</h1>
                        <p>by The Develovers</p>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<!-- END WRAPPER -->
</body>

</html>
