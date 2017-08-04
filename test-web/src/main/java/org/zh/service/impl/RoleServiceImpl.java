package org.zh.service.impl;

import org.springframework.stereotype.Service;
import org.zh.base.BaseService;
import org.zh.base.BaseServiceImpl;
import org.zh.bean.Role;
import org.zh.bean.RoleExample;
import org.zh.dao.RoleMapper;
import org.zh.service.IRoleService;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role, RoleExample> implements IRoleService {

}