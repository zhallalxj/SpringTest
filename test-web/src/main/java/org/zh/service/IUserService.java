package org.zh.service;

import org.zh.base.BaseService;
import org.zh.bean.User;
import org.zh.bean.UserExample;
import org.zh.exception.AccountNotExistException;
import org.zh.exception.TokenExpiredException;
import org.zh.exception.UserNotLoginException;
import org.zh.exception.WrongPasswordException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ZhaoHang on 2017/7/25.
 */
public interface IUserService extends BaseService<User, UserExample, Long> {

    User searchUser(HttpServletRequest request) throws UserNotLoginException, AccountNotExistException, TokenExpiredException;

    User searchByToken(String token);

    User login(String userName, String password, boolean rememberMe) throws AccountNotExistException, WrongPasswordException;
}
