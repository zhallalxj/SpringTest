package org.zh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zh.bean.Permission;
import org.zh.bean.PermissionExample;
import org.zh.service.IPermissionService;
import org.zh.utils.CustomResponse;
import org.zh.utils.DataTableJSONResponse;
import org.zh.utils.PageRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created with IntelliJ IDEA 2017.1.5. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/8/7  <br/>
 * Time: 15:24  <br/>
 *
 * @Description: 描述本类的作用
 */
@ApiIgnore
@Controller
@RequestMapping(value = "/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private DataTableJSONResponse dataTableJSONResponse;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public String index() {
        return "/permission/permission";
    }

    @RequestMapping(value = "/addPermission.html", method = RequestMethod.GET)
    public String addPermission() {
        return "/permission/addPermission";
    }

    @RequestMapping(value = "{id}/editPermission.html", method = RequestMethod.GET)
    public String edit(@PathVariable String id, Model model) {
        Permission permission = permissionService.selectByPrimaryKey(Long.valueOf(id));
        Permission parentPermission = permissionService.selectByPrimaryKey(permission.getParentid());
        if(parentPermission == null){
            parentPermission = new Permission();
            parentPermission.setId(10010L);
            parentPermission.setName("根目录");
        }
        model.addAttribute("permission", permission);
        model.addAttribute("parentPermission", parentPermission);
        return "/permission/editPermission";
    }

    @ResponseBody
    @RequestMapping(value = "/getAllPermission", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String getAllPermission() {
        CustomResponse customResponse = new CustomResponse();

        PermissionExample permissionExample = new PermissionExample();
        List<Permission> allPermissions = permissionService.selectByExample(permissionExample);

        List<Map<String, Object>> list = new LinkedList<>();

        Map<String, Object> mp = new HashMap<>();
        mp.put("open", true);
        mp.put("id", "10010");
        mp.put("name", "根目录");
        list.add(mp);

        for (Permission permission : allPermissions) {
            mp = new HashMap<>();
            mp.put("open", true);
            mp.put("id", permission.getId());
            mp.put("pId", permission.getParentid());
            mp.put("name", permission.getName());
            list.add(mp);
        }

        customResponse.addValue("data", list);

        return customResponse.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/listAjax", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String listAjax(HttpServletRequest request) {
        PageRequest pageRequest = new PageRequest(request);

        PermissionExample permissionExample = new PermissionExample();
        permissionExample.setLimit(pageRequest.getLimit());
        permissionExample.setOffset(pageRequest.getOffset());
        permissionExample.setOrderByClause(pageRequest.getOrderByClause());

        List<Permission> permissionList = permissionService.selectByExample(permissionExample);
        long count = permissionService.countByExample(permissionExample);

        return dataTableJSONResponse.toJSONString(pageRequest.getDraw(), count, count, permissionList);
    }

    @ResponseBody
    @RequestMapping(value = "/editPermission",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String editPermission(Permission permission){
        CustomResponse customResponse = new CustomResponse();

        permission.setModifyTime(new Date());
        int result = permissionService.updateByPrimaryKeySelective(permission);

        if(result == 0) return customResponse.getErrorJson("修改失败");

        return customResponse.getSuccessJson("修改成功");
    }


}
