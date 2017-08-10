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
                        <th width="250px">操作</th>
                    </tr>
                    </thead>


                </table>

            </div>
        </div>

    </div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
                <input type="hidden" id="userId" name="userId"/>
                <ul id="permissionTree" class="ztree"></ul>


            </div>
            <input type="hidden" id="roleId" name="roleId">
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="J_SUBMIT">
                    确认
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

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
                        html += "<button id='setRole' class='btn btn-primary btn-xs' data-id=" + row.id + ">设置角色</button>&nbsp";
                        html += "<button id='setPermission' class='btn btn-primary btn-xs' data-id=" + row.id + ">设置权限</button>&nbsp";
                        html += "<button id='delete' class='btn btn-danger btn-xs' data-id=" + row.id + ">删除</button>&nbsp";

                        return html
                    }
                }
            ],

            "language": {
                url:  '/static/assets/vendor/datatables/i18n/Chinese.json'
            }
        });

        $(document).delegate("#setPermission", "click", function (e) {
            var id = $(this).data("id");
            $.ajax({
                url: "/permission/getUserPermissionList/" + id,
                dataType: 'json',
                type: 'POST',
                async: false,
                success: function (rsp) {

                    if (rsp.status == 10000) {
                        $.fn.zTree.init($("#permissionTree"), setting, rsp.values.data);
                        $("#userId").val(id);
                        setCheck();

                        $('#myModal').modal('show');
                        $('#myModal').on('show.bs.modal', function () {

                        });
                    } else {
                        layer.msg(rsp.message);
                    }

                }
            });

        });

        $("#J_SUBMIT").click(function () {
            var zTreeObj = $.fn.zTree.getZTreeObj("permissionTree");
            var changeCount = zTreeObj.getChangeCheckedNodes().length;

            if (changeCount === 0) {
                layer.msg("修改成功");
                $('#myModal').modal('hide');
                return false;
            }

            var ids = [];
            $.each(zTreeObj.getCheckedNodes(), function (i, d) {
                ids[i] = d.id;
            });

            var id = $("#userId").val();

            /*
             传递数组到后台：
             这种方式比较重要的 traditional:true。或者将2、中的 _list参数转换一下$.param(_list,true)。
             这里其实就是将_list作为传统的方式传递给后台。jQuery默认是做了转换的。
             据说是为了使用PHP。。。。后台语言而做的。其实也就是自动在参数后面追加了”[]“。
             */
            $.ajax({
                url: "/permission/updateUserPermissionList/" + id,
                dataType: 'json',
                type: 'POST',
                data: {ids: ids},
                traditional: true,
                async: false,
                success: function (rsp) {
                    layer.msg(rsp.message);

                    if (rsp.status == 10000) {
                        $('#myModal').modal('hide');
                    }

                }
            });

        });


    });



    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    var code;
    function setCheck() {
        var zTree = $.fn.zTree.getZTreeObj("permissionTree"),
            py = $("#py").attr("checked") ? "p" : "",
            sy = $("#sy").attr("checked") ? "s" : "",
            pn = $("#pn").attr("checked") ? "p" : "",
            sn = $("#sn").attr("checked") ? "s" : "",
            type = {"Y": py + sy, "N": pn + sn};
        setting.check.chkboxType = {"Y": "ps", "N": "ps"};
        //zTree.setting.check.chkboxType = type;
        showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
    }
    function showCode(str) {
        if (!code) code = $("#code");
        code.empty();
        code.append("<li>" + str + "</li>");
    }

</script>

</body>

</html>
