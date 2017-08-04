package org.zh.service.impl;

import org.springframework.stereotype.Service;
import org.zh.base.BaseService;
import org.zh.base.BaseServiceImpl;
import org.zh.bean.Permission;
import org.zh.bean.PermissionExample;
import org.zh.dao.PermissionMapper;
import org.zh.service.IPermissionService;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission, PermissionExample> implements IPermissionService {

}