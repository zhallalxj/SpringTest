<%--
  Created by IntelliJ IDEA.
  User: ZhaoHang
  Date: 2017/8/2
  Time: 10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>注册</title>
</head>
<body>

<c:if test="${not empty message}">
    <label style="color: red">${message}</label>
</c:if>

<form action="${basePath}/register" method="post">

    <div>
        <label for="username">用户名：</label>
        <input id="username" name="username">
    </div>
    <div>
        <label for="username">密码：</label>
        <input id="password" name="password">
    </div>
    <button type="submit">注册</button>
</form>


</body>
</html>
