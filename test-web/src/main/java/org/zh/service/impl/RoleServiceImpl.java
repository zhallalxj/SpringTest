package org.zh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zh.base.BaseService;
import org.zh.base.BaseServiceImpl;
import org.zh.bean.Role;
import org.zh.bean.RoleExample;
import org.zh.bean.RolePermission;
import org.zh.bean.RolePermissionExample;
import org.zh.dao.RoleMapper;
import org.zh.service.IRolePermissionService;
import org.zh.service.IRoleService;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role, RoleExample> implements IRoleService {

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Override
    public Map changeRolePermission(Long roleId, List<Long> permissionId) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("state", 0);

        Role role = selectByPrimaryKey(roleId);
        if (role == null) {
            resultMap.put("message", "角色不存在");
            return resultMap;
        }

        //去除List中的重复和空值
        List<Long> difList = new ArrayList<>();
        for (Long t : permissionId) {
            if (t != null && !difList.contains(t)) {
                difList.add(t);
            }
        }

        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = rolePermissionExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andUserIdIsNull();
        rolePermissionService.deleteByExample(rolePermissionExample);

        for (Long id : difList) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(id);
            rolePermissionService.insertSelective(rolePermission);
        }

        resultMap.put("state", 1);
        return resultMap;
    }

    @Override
    public Map deleteRole(Long roleId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("state", 0);


        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = rolePermissionExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andUserIdIsNull();
        rolePermissionService.deleteByExample(rolePermissionExample);

        this.deleteByPrimaryKey(roleId);

        resultMap.put("state", 1);
        return resultMap;
    }
}