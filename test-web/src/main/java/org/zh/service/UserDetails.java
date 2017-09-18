package org.zh.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zh.exception.UserNotLoginException;
import org.zh.bean.CustomUserDetails;

/**
 * Created with IntelliJ IDEA 2017.1.2. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/7/12  <br/>
 * Time: 11:21  <br/>
 *
 * @Description: 用一句话描述
 */
public class UserDetails {


    public static CustomUserDetails getCustomUserDetails() throws UserNotLoginException {
        CustomUserDetails user;
        try {
            user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            throw new UserNotLoginException(10001, "请先登录再进行操作!", e);
        }
        return user;
    }

}
