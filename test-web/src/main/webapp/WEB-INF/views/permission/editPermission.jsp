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

                <form:form action="${basePath}/permission/editPermission" method="POST" modelAttribute="permission"
                           name="J_PERMISSION_EDIT_FORM" id="J_PERMISSION_EDIT_FORM">
                    <input type="hidden" name="id" value="${permission.id}">
                    <!-- 礼包创建 -->
                    <div class="col-lg-12">

                        <div class="form-group col-sm-12">

                            <label class="col-sm-2 control-label">权限名：</label>
                            <div class=" input-group col-sm-4">
                                <input type="text" name="name" class="form-control" id="name" value="${permission.name}"
                                       placeholder="角色名">
                            </div>
                        </div>


                        <div class="form-group col-sm-12">
                            <label class=" col-sm-2 control-label">URL：</label>
                            <div class=" input-group col-sm-4">

                                <input type="text" class="form-control" name="uri" id="uri"
                                       placeholder="uri" value="${permission.uri}">

                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class=" col-sm-2 control-label">权限值：</label>
                            <div class=" input-group col-sm-4">

                                <input type="text" class="form-control" name="permissionValue" id="permissionValue"
                                       placeholder="权限值" value="${permission.permissionValue}">

                            </div>
                        </div>


                        <div class="form-group col-sm-12">
                            <label class=" col-sm-2 control-label">父级菜单：</label>
                            <div class=" input-group col-sm-4">

                                <input type="text" class="form-control" name="parent" id="parent"
                                       placeholder="父级菜单" value="${parentPermission.name}" readonly>
                                <input type="hidden" id="parentid" name="parentid" value="${parentPermission.id}">
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class=" col-sm-2 control-label">icon：</label>
                            <div class=" input-group col-sm-4">

                                <input type="text" class="form-control" name="icon" id="icon"
                                       placeholder="icon" value="${permission.icon}">

                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class=" col-sm-2 control-label">类型：</label>
                            <div class=" input-group col-sm-4">
                                <label class="fancy-radio">
                                    <input name="type" value="0" type="radio"
                                           <c:if test="${permission.type == 0}">checked</c:if>>
                                    <span><i></i>目录</span>
                                </label>
                                <label class="fancy-radio">
                                    <input name="type" value="1" type="radio"
                                           <c:if test="${permission.type == 1}">checked</c:if>>
                                    <span><i></i>菜单</span>
                                </label>
                                <label class="fancy-radio">
                                    <input name="type" value="2" type="radio"
                                           <c:if test="${permission.type == 2}">checked</c:if>>
                                    <span><i></i>按钮</span>
                                </label>

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
                <input type="hidden" id="rId" name="rId"/>
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
<script src="${basePath}/static/assets/vendor/jquery/jquery-form.js"></script>
<script src="${basePath}/static/assets/vendor/bootstrapValidator/js/bootstrapValidator.min.js"></script>
<script src="${basePath}/static/assets/vendor/jquery/jquery.ztree.core.min.js"></script>
<script src="${basePath}/static/assets/vendor/jquery/jquery.ztree.excheck.min.js"></script>
<script>

    var setting = {
        check: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick
        }
    };

    var zTreeSelectId;
    var zTreeSelectName;

    function onClick(event, treeId, treeNode, clickFlag) {
        var treeObj = $.fn.zTree.getZTreeObj('permissionTree');
        treeObj.cancelSelectedNode();
        var node = treeObj.getNodeByParam("id", treeNode.id);
        treeObj.selectNode(node);
        zTreeSelectId = treeNode.id;
        zTreeSelectName = treeNode.name;
    }


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

    $(function () {

        $("#J_SUBMIT").click(function () {
            $("#parentid").val(zTreeSelectId);
            $("#parent").val(zTreeSelectName);
            $('#myModal').modal('hide');
        });

        $("#parent").click(function () {

            var id = $("#parentid").val();

            $.ajax({
                url: "/permission/getAllPermission",
                dataType: 'json',
                type: 'POST',
                async: false,
                success: function (rsp) {

                    if (rsp.status == 10000) {
                        $.fn.zTree.init($("#permissionTree"), setting, rsp.values.data);
                        var zTree = $.fn.zTree.getZTreeObj("permissionTree");
                        var node = zTree.getNodeByParam("id", id);
                        zTree.selectNode(node);

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


        $('#J_PERMISSION_EDIT_FORM').bootstrapValidator({
            message: 'This value is not valid',
            fields: {
                name: {
                    validators: {
                        notEmpty: {
                            message: '权限名不能为空'
                        }
                    }
                },
                permissionValue: {
                    validators: {
                        notEmpty: {
                            message: '权限值不能为空'
                        },
                        regexp: {//匹配规则
                            regexp: /^\+?[A-Za-z_]*$/,
                            message: '只能输入字母和下划线'
                        }
                    }
                },
                uri: {
                    validators: {
                        notEmpty: {
                            message: 'URL不能为空'
                        }
                    }
                },
                parentid: {
                    validators: {
                        notEmpty: {
                            message: '父级菜单不能为空'
                        }
                    }
                },
                type: {
                    validators: {
                        notEmpty: {
                            message: '类型不能为空'
                        }
                    }
                }

            }
        }).on('success.form.bv', function (e) {

            e.preventDefault();
            var $form = $(e.target);
            var bv = $form.data('bootstrapValidator');

            $('#J_PERMISSION_EDIT_FORM').ajaxSubmit({
                semantic: true,
                dataType: 'json',
                success: function (data) {
                    console.info(data);
                    layer.msg(data.message);
                    if (data.status === 10000) {

                        setTimeout(function () {
                            window.location.href = '/permission/list.html';
                        }, 1000);

                    }
                }
            });

        });
    });

</script>

</body>

</html>
