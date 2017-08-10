package org.zh.service.impl;

import org.springframework.stereotype.Service;
import org.zh.base.BaseService;
import org.zh.base.BaseServiceImpl;
import org.zh.bean.UserRole;
import org.zh.bean.UserRoleExample;
import org.zh.dao.UserRoleMapper;
import org.zh.service.IUserRoleService;
import org.zh.service.IUserService;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole, UserRoleExample> implements IUserRoleService {

}