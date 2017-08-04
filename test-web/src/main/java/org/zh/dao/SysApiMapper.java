package org.zh.dao;

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
public interface SysApiMapper {

    List<Permission> selectUserPermissionList(@Param("userId") Long userId);

    List<Permission> selectPermissionListByRoleId(@Param("roleId") Long roleId);

}
