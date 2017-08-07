package org.zh.service.impl;

import org.springframework.stereotype.Service;
import org.zh.base.BaseService;
import org.zh.base.BaseServiceImpl;
import org.zh.bean.RolePermission;
import org.zh.bean.RolePermissionExample;
import org.zh.dao.RolePermissionMapper;
import org.zh.service.IRolePermissionService;

@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermissionMapper, RolePermission, RolePermissionExample> implements IRolePermissionService {

}