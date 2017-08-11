package org.zh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zh.bean.Permission;
import org.zh.bean.PermissionExample;
import org.zh.constants.PermissionUtils;
import org.zh.service.IPermissionService;
import org.zh.service.ISysApiService;
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
    private ISysApiService sysApiService;

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
        if (parentPermission == null) {
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
    @RequestMapping(value = "/editPermission", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String editPermission(Permission permission) {
        CustomResponse customResponse = new CustomResponse();

        permission.setModifyTime(new Date());
        int result = permissionService.updateByPrimaryKeySelective(permission);

        if (result == 0) return customResponse.getErrorJson("修改失败");

        return customResponse.getSuccessJson("修改成功");
    }

    @ResponseBody
    @RequestMapping(value = "/addPermission", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String addPermission(Permission permission) {
        CustomResponse customResponse = new CustomResponse();

        permission.setCreateTime(new Date());
        permission.setModifyTime(new Date());

        PermissionExample permissionExample = new PermissionExample();
        permissionExample.setOrderByClause("level DESC");
        Permission per = permissionService.selectFirstByExample(permissionExample);
        if (per == null) {
            permission.setLevel(1);
        } else {
            permission.setLevel(per.getLevel() + 1);
        }

        int result = permissionService.insertSelective(permission);

        if (result == 0) return customResponse.getErrorJson("添加失败");

        return customResponse.getSuccessJson("添加成功");
    }

    @ResponseBody
    @RequestMapping(value = "/getUserPermissionList/{userId}", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=utf-8")
    public String getUserPermissionList(@PathVariable String userId) {
        CustomResponse customResponse = new CustomResponse();

        List<Permission> permissions = sysApiService.selectPermissionListByUserId(Long.valueOf(userId));
        PermissionExample permissionExample = new PermissionExample();
        List<Permission> allPermissions = permissionService.selectByExample(permissionExample);

        List<Map<String, Object>> list = PermissionUtils.getCheckPermissionList(permissions, allPermissions);
        customResponse.addValue("data", list);
        return customResponse.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/updateUserPermissionList/{userId}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String updateUserPermissionList(@PathVariable String userId, @RequestParam(required = false) Long[] ids) {
        CustomResponse customResponse = new CustomResponse();

        try {

            List<Long> list = ids != null ? Arrays.asList(ids) : new ArrayList<Long>();
            Map resultMap = permissionService.updateUserPermissionList(Long.parseLong(userId), list);
            if ((int) resultMap.get("state") == 0) {
                return customResponse.getErrorJson((String) resultMap.get("message"));
            }
        } catch (Exception e) {
            return customResponse.getErrorJson("修改失败");
        }

        return customResponse.getSuccessJson("修改成功");
    }

}
