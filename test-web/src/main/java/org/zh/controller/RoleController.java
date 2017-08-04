package org.zh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zh.bean.Permission;
import org.zh.bean.PermissionExample;
import org.zh.bean.Role;
import org.zh.bean.RoleExample;
import org.zh.service.IPermissionService;
import org.zh.service.IRoleService;
import org.zh.service.ISysApiService;
import org.zh.utils.CustomResponse;
import org.zh.utils.DataTableJSONResponse;
import org.zh.utils.PageRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private DataTableJSONResponse dataTableJSONResponse;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public String role() {
        return "/role/role";
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


        List<Map<String,Object>> list = new LinkedList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("var data = [];");
        for (Permission permission : allPermissions) {
            boolean flag = false;
            for (Permission p : rolePermissions) {//用户拥有的权限
                if (p.getId().equals(permission.getId())) {
                    Map<String,Object> mp= new HashMap<>();
                    mp.put("id",permission.getId());
                    mp.put("pId",permission.getParentid());
                    mp.put("name",permission.getName());
                    mp.put("checked",true);
                    mp.put("open",true);
                    list.add(mp);
                    list.add(mp);list.add(mp);list.add(mp);list.add(mp);list.add(mp);list.add(mp);list.add(mp);list.add(mp);list.add(mp);list.add(mp);list.add(mp);list.add(mp);
                    flag = true;
                }
            }
            if (!flag) {
                Map<String,Object> mp= new HashMap<>();
                mp.put("id",permission.getId());
                mp.put("pId",permission.getParentid());
                mp.put("name",permission.getName());
                mp.put("open",true);
                list.add(mp);

            }
        }

        customResponse.addValue("data", list);

        return customResponse.toJSONString();
    }


}
