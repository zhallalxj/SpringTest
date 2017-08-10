package org.zh.service;

import org.apache.ibatis.annotations.Param;
import org.zh.base.BaseService;
import org.zh.bean.UserRole;
import org.zh.bean.UserRoleExample;

import java.util.List;

public interface IUserRoleService extends BaseService<UserRole, UserRoleExample, Long> {

}