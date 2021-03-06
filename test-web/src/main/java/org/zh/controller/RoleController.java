package org.zh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zh.bean.*;
import org.zh.constants.PermissionUtils;
import org.zh.service.IPermissionService;
import org.zh.service.IRoleService;
import org.zh.service.ISysApiService;
import org.zh.service.IUserRoleService;
import org.zh.utils.CustomResponse;
import org.zh.utils.DataTableJSONResponse;
import org.zh.utils.PageRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created with IntelliJ IDEA 2017.1.5. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/8/4  <br/>
 * Time: 10:23  <br/>
 *
 * @Description: 描述本类的作用
 */
@ApiIgnore
@Controller
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ISysApiService sysApiService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private DataTableJSONResponse dataTableJSONResponse;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public String role() {
        return "/role/role";
    }

    @RequestMapping(value = "/addRole.html", method = RequestMethod.GET)
    public String addRole() {
        return "/role/addRole";
    }

    @ResponseBody
    @RequestMapping(value = "/listAjax", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String listAjax(HttpServletRequest request) {
        PageRequest pageRequest = new PageRequest(request);

        RoleExample roleExample = new RoleExample();
        roleExample.setLimit(pageRequest.getLimit());
        roleExample.setOffset(pageRequest.getOffset());
        roleExample.setOrderByClause(pageRequest.getOrderByClause());

        List<Role> roles = roleService.selectByExample(roleExample);
        long count = roleService.countByExample(roleExample);


        return dataTableJSONResponse.toJSONString(pageRequest.getDraw(), count, count, roles);
    }

    @ResponseBody
    @RequestMapping(value = "/getPermissionList/{roleId}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String getPermissionListByRoleId(@PathVariable Long roleId) {
        CustomResponse customResponse = new CustomResponse();

        List<Permission> rolePermissions = sysApiService.selectPermissionListByRoleId(roleId);

        PermissionExample permissionExample = new PermissionExample();
        List<Permission> allPermissions = permissionService.selectByExample(permissionExample);

        List<Map<String, Object>> list = PermissionUtils.getCheckPermissionList(rolePermissions, allPermissions);

        customResponse.addValue("data", list);

        return customResponse.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/updatePermission/{roleId}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String updateRolePermission(@PathVariable String roleId, @RequestParam(required = false) Long[] ids) {
        CustomResponse customResponse = new CustomResponse();

        try {
            List<Long> list = ids != null ? Arrays.asList(ids) : new ArrayList<Long>();
            Map resultMap = roleService.changeRolePermission(Long.parseLong(roleId), list);
            if ((int) resultMap.get("state") == 0) {
                return customResponse.getErrorJson((String) resultMap.get("message"));
            }
        } catch (Exception e) {
            return customResponse.getErrorJson("修改失败");
        }


        return customResponse.getSuccessJson("修改成功");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteRole/{roleId}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String deleteRole(@PathVariable String roleId) {
        CustomResponse customResponse = new CustomResponse();

        try {
            Map resultMap = roleService.deleteRole(Long.valueOf(roleId));
            if ((int) resultMap.get("state") == 0) {
                return customResponse.getErrorJson((String) resultMap.get("message"));
            }
        } catch (Exception e) {
            return customResponse.getErrorJson("删除角色失败");
        }


        return customResponse.getSuccessJson("删除角色成功");
    }

    @ResponseBody
    @RequestMapping(value = "/addRole", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String addRole(Role role) {
        role.setCreateTime(new Date());
        role.setModifyTime(new Date());
        CustomResponse customResponse = new CustomResponse();
        int result = roleService.insertSelective(role);

        if (result == 0) return customResponse.getErrorJson("添加失败");

        return customResponse.getSuccessJson("添加成功");
    }

    @ResponseBody
    @RequestMapping(value = "/getUserRoleId/{userId}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String getUserRoleId(@PathVariable String userId) {
        CustomResponse customResponse = new CustomResponse();

        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(userId));

        UserRole userRole = userRoleService.selectFirstByExample(userRoleExample);
        customResponse.addValue("userRole", userRole);
        return customResponse.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/updateUserRole/{userId}/{roleId}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String updateUserRole(@PathVariable String userId, @PathVariable String roleId) {
        CustomResponse customResponse = new CustomResponse();

        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(userId));

        UserRole userRole = userRoleService.selectFirstByExample(userRoleExample);
        int result;
        if (userRole == null) {
            userRole = new UserRole();
            userRole.setUserId(Long.valueOf(userId));
            userRole.setRoleId(Long.valueOf(roleId));
            result = userRoleService.insertSelective(userRole);
        } else {
            userRole.setRoleId(Long.valueOf(roleId));
            result = userRoleService.updateByPrimaryKeySelective(userRole);
        }
        if (result == 0) return customResponse.getErrorJson("修改角色失败");

        return customResponse.getSuccessJson("修改角色成功，重新登录生效");
    }

}
