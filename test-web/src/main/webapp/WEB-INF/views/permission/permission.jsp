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
    <link rel="stylesheet" href="${basePath}/static/assets/vendor/jquery/zTreeStyle/zTreeStyle.css">
</head>

<body>
<!-- WRAPPER -->
<div class="panel panel-headline">
    <div class="panel-heading">
        <h3 class="panel-title">权限管理</h3>
        <p class="panel-subtitle">Period: Oct 14, 2016 - Oct 21, 2016</p>
    </div>
    <div class="panel-body">

        <div class="row">
            <div class="col-lg-12">


                <table width="100%" class="display table  table-bordered table-hover" id="dataTables-example">
                    <thead>
                    <tr>

                        <th>ID</th>
                        <th>权限名称</th>
                        <th>URL</th>
                        <th>权限值</th>
                        <th>类型</th>
                        <th>优先级</th>
                        <th width="200px">操作</th>
                    </tr>
                    </thead>


                </table>

            </div>
        </div>

    </div>
</div>


<%--<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" style="text-align: center;" id="myModalLabel">
                    选择权限
                </h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="rId" name="rId"/>
                <ul id="treeDemo" class="ztree"></ul>


            </div>
            <input type="hidden" id="roleId" name="roleId">
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="J_SUBMIT">
                    提交保存
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>--%>


<!-- END WRAPPER -->
<!-- Javascript -->
<%@include file="../base/web-js-base.jsp" %>
<script src="${basePath}/static/assets/vendor/jquery/jquery.ztree.core.min.js"></script>
<script src="${basePath}/static/assets/vendor/jquery/jquery.ztree.excheck.min.js"></script>
<script>

    $(function () {
        var table = $('#dataTables-example').DataTable({
            "responsive": true,
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": '/permission/listAjax',
                "type": 'POST',
                "dataType": "json",
                "statusCode": {
                    404: function () {
                        layer.msg("您访问的页面不存在");
                    },
                    500: function () {
                        layer.msg("未知错误，请稍后再试");
                    }

                },
                "dataFilter": function (result, type) {
                    var json = jQuery.parseJSON(result);
                    if (json.status != 10000) {
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
            "order": [[0, "DESC"]],
            "searching": false,
            "columns": [
                {"data": "id"},
                {"data": "name"},
                {"data": "uri"},
                {"data": "permission_value"},
                {"data": "type"},
                {"data": "level"}

            ], columnDefs: [

                {
                    "targets": 6,
                    "data": null,
                    "searchable": false,
                    "bSortable": false,
                    "render": function (data, type, row, meta) {
                        var html = "";

                        html += "<a href='/permission/"+row.id+"/editPermission.html' class='btn btn-primary btn-xs'>编辑权限</a>&nbsp";
                        html += "<button id='delete' class='btn btn-danger btn-xs' data-id=" + row.id + ">删除</button>&nbsp";

                        return html
                    }
                }
            ],

            "language": {
                url: '/static/assets/vendor/datatables/i18n/Chinese.json'
            }

        });



    });


</script>

</body>

</html>
