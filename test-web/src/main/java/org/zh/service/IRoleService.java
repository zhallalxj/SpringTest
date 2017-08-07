package org.zh.service;

import org.zh.base.BaseService;
import org.zh.bean.Role;
import org.zh.bean.RoleExample;

import java.util.List;
import java.util.Map;


public interface IRoleService extends BaseService<Role, RoleExample, Long> {

    Map changeRolePermission(Long roleId, List<Long> permissionId);

    Map deleteRole(Long roleId);
}