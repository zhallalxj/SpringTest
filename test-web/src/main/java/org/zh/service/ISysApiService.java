package org.zh.service;

import org.apache.ibatis.annotations.Param;
import org.zh.bean.Permission;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2017.1.5. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/8/3  <br/>
 * Time: 11:16  <br/>
 *
 * @Description: 描述本类的作用
 */
public interface ISysApiService {

    List<Permission> selectUserPermissionList(Long userId);

    List<Permission> selectPermissionListByRoleId(Long roleId);

    List<Permission> selectPermissionListByUserId(Long userId);
}
