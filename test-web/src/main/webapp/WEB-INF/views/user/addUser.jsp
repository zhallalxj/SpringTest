<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">

<head>
    <title>Dashboard | Klorofil - Free Bootstrap Dashboard Template</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <%@include file="../base/web-css-base.jsp" %>
</head>

<body>
<!-- WRAPPER -->
<div class="panel panel-headline">
    <div class="panel-heading">
        <h3 class="panel-title">用户管理</h3>
        <p class="panel-subtitle">Period: Oct 14, 2016 - Oct 21, 2016</p>
    </div>
    <div class="panel-body">

        <div class="row">
            <div class="col-lg-12">

                <form:form action="${basePath}/user/addUser" method="POST" modelAttribute="user"
                           name="J_USER_ADD_FORM" id="J_USER_ADD_FORM">

                    <!-- 礼包创建 -->
                    <div class="col-lg-12">

                        <div class="form-group col-sm-12">

                            <label class="col-sm-2 control-label">用户名：</label>
                            <div class=" input-group col-sm-4">
                                <input type="text" name="userName" autocomplete="new-password" class="form-control"
                                       id="userName" placeholder="用户名">
                            </div>
                        </div>
                        <div class="form-group col-sm-12">
                            <label class=" col-sm-2 control-label">密码：</label>
                            <div class=" input-group col-sm-4">

                                <input type="password" class="form-control" autocomplete="new-password" name="password"
                                       id="password" value="" placeholder="密码">

                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-2 control-label"></label>
                            <div class="input-group col-sm-4">
                                <button type="submit" id="submitBtn" class="btn btn-primary btn-lg btn-block btn-save">
                                    保存
                                </button>

                            </div>

                        </div>


                    </div>

                </form:form>

            </div>
        </div>

    </div>
</div>
<!-- END WRAPPER -->
<!-- Javascript -->
<%@include file="../base/web-js-base.jsp" %>
<script src="${basePath}/static/assets/vendor/jquery/jquery-form.js"></script>
<script src="${basePath}/static/assets/vendor/bootstrapValidator/js/bootstrapValidator.min.js"></script>


<script>

    $('#J_USER_ADD_FORM').bootstrapValidator({
        message: 'This value is not valid',
        fields: {
            userName: {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 16,
                        message: '密码长度必须介于1到32之间'
                    }
                }
            }

        }
    }).on('success.form.bv', function (e) {

        e.preventDefault();
        var $form = $(e.target);
        var bv = $form.data('bootstrapValidator');

        $('#J_USER_ADD_FORM').ajaxSubmit({
            semantic: true,
            dataType: 'json',
            success: function (data) {
                console.info(data);
                layer.msg(data.message);
                if (data.status === 10000) {

                    setTimeout(function () {
                        window.location.href = '/user/list.html';
                    }, 1000);

                } else {

                }
            }
        });

    });




</script>

</body>

</html>
