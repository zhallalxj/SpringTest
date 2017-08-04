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

                <table width="100%" class="display table  table-bordered table-hover"  id="dataTables-example">
                    <thead>
                    <tr>

                        <th>ID</th>
                        <th>用户名</th>
                        <th>创建时间</th>
                        <th>修改时间</th>
                        <th width="150px">操作</th>
                    </tr>
                    </thead>


                </table>

            </div>
        </div>

    </div>
</div>
<!-- END WRAPPER -->
<!-- Javascript -->
<%@include file="../base/web-js-base.jsp" %>

<script>
    var table = $('#dataTables-example').DataTable({
        "responsive": true,
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url":  '/user/listAjax',
            "type": 'POST',
            "dataType": "json",
            "statusCode": {
                404: function() {layer.msg("您访问的页面不存在");},
                500: function() {layer.msg("未知错误，请稍后再试");}

            },
            "dataFilter": function(result, type){
                var json = jQuery.parseJSON( result );
                if(json.status !=10000){
                    layer.msg(json.message);
                    return false;
                }
                return result;
            },

            "data": function (d) {
                //添加额外的参数传给服务器
            }
        },
        "pagingType": "full_numbers",
        "stripeClasses": ["odd", "even"],
        "order": [[0, "desc"]],
        "searching": false,
        "columns": [
            {"data": "id"},
            {"data": "user_name"},
            {"data": "create_time"},
            {"data": "update_time"}

        ], columnDefs: [

            {
                "targets": 4,
                "data": null,
                "searchable": false,
                "bSortable": false,
                "render": function (data, type, row, meta) {
                    var html = "";

                    html += "<button id='delete' class='btn btn-primary btn-xs' data-id=" + row.id + ">删除</button>&nbsp";

                    return html
                }
            }
        ],

        "language": {
            url:  '/static/assets/vendor/datatables/i18n/Chinese.json'
        }

    });
</script>

</body>

</html>