package org.zh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.zh.bean.CustomUserDetails;
import org.zh.bean.Permission;
import org.zh.bean.User;
import org.zh.bean.UserExample;
import org.zh.service.ISysApiService;
import org.zh.service.IUserService;

import java.util.*;

/**
 * Created by ZhaoHang on 2017/7/25.
 */
@Component
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysApiService sysApiService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(username);

        User user = userService.selectFirstByExample(userExample);

        List<GrantedAuthority> authorities = obtionGrantedAuthorities(user.getId());

        CustomUserDetails customUserDetails = new CustomUserDetails(user.getUserName(), user.getPassword(), true, true, true,
                true, authorities) {
        };
        customUserDetails.setId(user.getId());

        return customUserDetails;
    }

    private List<GrantedAuthority> obtionGrantedAuthorities(Long userId) {
        List<Permission> permissions = sysApiService.selectUserPermissionList(userId);
        List<GrantedAuthority> grantedAuthorities = new LinkedList<>();
        for (Permission permission : permissions) {
            // TODO:ZZQ 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + permission.getPermissionValue()));
        }
        return grantedAuthorities;
    }

}
