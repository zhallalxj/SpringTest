<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">

<head>
    <title>Dashboard | Klorofil - Free Bootstrap Dashboard Template</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!-- VENDOR CSS -->
    <%@include file="base/web-css-base.jsp" %>
</head>

<body style="background-color: #2B333E">
<div id="sidebar-nav" class="sidebar">
    <div class="sidebar-scroll">
        <nav>
            <ul class="nav">

                <c:forEach items="${permissions}" var="key"
                           varStatus="stut">
                    <c:if test="${key.parentid eq 10010}">
                        <li style="background-color: #2B333E" data-id="${key.id}">
                            <c:set var="icon" value="lnr-file-empty"/>
                            <c:if test="${not empty key.icon}">
                                <c:set var="icon" value="${key.icon}"/>
                            </c:if>

                            <c:choose>
                                <c:when test="${key.type == 1}">
                                    <a href="${key.uri}" target="center"
                                       <c:if test="${stut.index == 0}">class="active"</c:if>><i
                                            class="lnr lnr-home"></i> <span>${key.name}</span></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${key.uri}" data-toggle="collapse"
                                       <c:if test="${stut.index == 0}">class="active"</c:if> ><i
                                            class="lnr ${icon}"></i>
                                        <span>${key.name}</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>
                                </c:otherwise>
                            </c:choose>

                            <ul class="nav">
                                <c:forEach items="${permissions}" var="k">
                                    <c:if test="${k.parentid eq key.id}">
                                        <li><a target="center" href="${pageContext.servletContext.contextPath}${k.uri}"
                                               class="">${k.name}</a></li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>
                </c:forEach>

            </ul>
        </nav>


    </div>
</div>


</body>
<script src="${basePath}/static/assets/vendor/jquery/jquery.min.js"></script>
<script src="${basePath}/static/assets/vendor/bootstrap/js/bootstrap.min.js"></script>

</html>
