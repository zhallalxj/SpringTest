package org.zh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zh.bean.Permission;
import org.zh.dao.SysApiMapper;
import org.zh.service.ISysApiService;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2017.1.5. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/8/3  <br/>
 * Time: 11:16  <br/>
 *
 * @Description: 描述本类的作用
 */
@Service
public class SysApiServiceImpl implements ISysApiService {

    @Autowired
    private SysApiMapper sysApiMapper;

    @Override
    public List<Permission> selectUserPermissionList(Long userId) {
        return sysApiMapper.selectUserPermissionList(userId);
    }

    @Override
    public List<Permission> selectPermissionListByRoleId(Long roleId) {
        return sysApiMapper.selectPermissionListByRoleId(roleId);
    }
}
