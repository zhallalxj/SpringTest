package org.zh.constants;

import org.zh.bean.Permission;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA 2017.1.5. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/8/10  <br/>
 * Time: 15:02  <br/>
 *
 * @Description: 描述本类的作用
 */
public class PermissionUtils {

    public static List<Map<String, Object>> getCheckPermissionList(List<Permission> checkPermission, List<Permission> allPermission) {

        List<Map<String, Object>> list = new LinkedList<>();

        for (Permission permission : allPermission) {
            boolean flag = false;
            for (Permission p : checkPermission) {//用户拥有的权限
                if (p.getId().equals(permission.getId())) {
                    Map<String, Object> mp = new HashMap<>();
                    mp.put("id", permission.getId());
                    mp.put("pId", permission.getParentid());
                    mp.put("name", permission.getName());
                    mp.put("checked", true);
                    mp.put("open", true);
                    list.add(mp);
                    flag = true;
                }
            }
            if (!flag) {
                Map<String, Object> mp = new HashMap<>();
                mp.put("id", permission.getId());
                mp.put("pId", permission.getParentid());
                mp.put("name", permission.getName());
                mp.put("open", true);
                list.add(mp);

            }
        }
        return list;
    }


}
