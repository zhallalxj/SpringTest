package org.zh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zh.base.BaseService;
import org.zh.base.BaseServiceImpl;
import org.zh.bean.*;
import org.zh.dao.PermissionMapper;
import org.zh.service.IPermissionService;
import org.zh.service.IRolePermissionService;
import org.zh.service.IUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission, PermissionExample> implements IPermissionService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Override
    public Map updateUserPermissionList(Long userId, List<Long> ids) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("state", 0);

        User user = userService.selectByPrimaryKey(userId);
        if (user == null) {
            resultMap.put("message","用户不存在");
            return resultMap;
        }

        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = rolePermissionExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        rolePermissionService.deleteByExample(rolePermissionExample);

        for (Long id : ids) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setUserId(userId);
            rolePermission.setPermissionId(id);
            rolePermissionService.insertSelective(rolePermission);
        }

        resultMap.put("state", 1);
        return resultMap;
    }
}