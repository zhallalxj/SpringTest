package org.zh.service;

import org.apache.ibatis.annotations.Param;
import org.zh.base.BaseService;
import org.zh.bean.Permission;
import org.zh.bean.PermissionExample;

import java.util.List;
import java.util.Map;

public interface IPermissionService extends BaseService<Permission, PermissionExample, Long> {

    Map updateUserPermissionList(Long userId,List<Long> ids);

}