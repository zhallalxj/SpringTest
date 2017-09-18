package org.zh.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zh.auth.AuthTokenGenerate;
import org.zh.auth.AuthorizationTokenValidate;
import org.zh.auth.Digests;
import org.zh.auth.Encodes;
import org.zh.base.BaseServiceImpl;
import org.zh.bean.CustomUserDetails;
import org.zh.bean.User;
import org.zh.bean.UserExample;
import org.zh.constants.CommonConstants;
import org.zh.dao.UserMapper;
import org.zh.exception.AccountNotExistException;
import org.zh.exception.TokenExpiredException;
import org.zh.exception.UserNotLoginException;
import org.zh.exception.WrongPasswordException;
import org.zh.service.IUserService;
import org.zh.service.UserDetails;
import org.zh.utils.DateUtil;
import org.zh.utils.JsonUtil;
import org.zh.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.zh.constants.CommonConstants.EXPIRE_DATE;
import static org.zh.constants.CommonConstants.REMEMBER_ME_EXPIRE_DATE;

/**
 * Created by ZhaoHang on 2017/7/25.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, UserExample> implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorizationTokenValidate authorizationTokenValidate;

    /**
     * 获取用户信息 先判断request中是否传入了Token，如果传入了直接使用，如果没有则拿取用户登录信息
     *
     * @param request request
     * @return User
     * @throws UserNotLoginException 用户未登录
     * @throws AccountNotExistException 用户不存在
     * @throws TokenExpiredException Token已失效
     */
    public User searchUser(HttpServletRequest request) throws UserNotLoginException, AccountNotExistException, TokenExpiredException {

        String token = request.getParameter(CommonConstants.AUTH_TOKEN);
        if (null == token) {
            CustomUserDetails userDetails = UserDetails.getCustomUserDetails();
            token = userDetails.getToken();
        }

        return authorizationTokenValidate.validate(token);

    }

    @Override
    public User searchByToken(String token) {
        if (StringUtils.isBlank(token)) return null;
        User user = RedisUtil.getData(token, User.class);
        if (user == null) {   // 直接从token将user add to redis

            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andTokenEqualTo(token);
            user = this.selectFirstByExample(userExample);

            // add to redis
            Date currentDate = new Date();
            if (user != null) {
                if (currentDate.before(user.getExpireDate())) {
                    RedisUtil.addData(user.getToken(), EXPIRE_DATE * 3600, JsonUtil.toJson(user));
                } else {
                    user = null;
                }
            }
        }
        return user;
    }

    /**
     * 登录
     * @param userName 用户名
     * @param password 密码
     * @param rememberMe 是否记住我
     * @return
     * @throws AccountNotExistException
     * @throws WrongPasswordException
     */
    @Override
    public User login(String userName, String password, boolean rememberMe) throws AccountNotExistException, WrongPasswordException {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(userName);

        User loginUser = this.selectFirstByExample(userExample);
        if (loginUser == null) {
            LOGGER.warn("account not exist: {}", userName);
            throw new AccountNotExistException("account not exist");
        }
        /*if (CommonConstants.ACCOUNT_STATUS_BANN == loginUser.getStatus()) {
            logger.warn("account is banned. account is " + user.getAccount());
            throw new AccountBannedException("account is banned");
        }*/

        boolean r = passwordEncoder.matches(password, loginUser.getPassword());

        if (!r) {
            LOGGER.warn("wrong password. account is {}", userName);
            throw new WrongPasswordException("密码错误");
        }


        // delete old token
        RedisUtil.del(loginUser.getToken());

        loginUser.setLastLoginDate(loginUser.getLoginDate());
        loginUser.setLoginDate(new Date());
        if (StringUtils.isEmpty(loginUser.getSalt())) {
            byte[] salt = Digests.generateSalt(8);
            loginUser.setSalt(Encodes.encodeHex(salt));
        }

        loginUser.setToken(AuthTokenGenerate.generateToken(loginUser.getUserName(), loginUser.getSalt()));
        loginUser.setExpireDate(DateUtil.hourOffset(new Date(), rememberMe ? REMEMBER_ME_EXPIRE_DATE : EXPIRE_DATE));
        loginUser.setVisitDate(new Date());
        this.updateByPrimaryKey(loginUser);

        // add to redis
        RedisUtil.addData(loginUser.getToken(), rememberMe ? REMEMBER_ME_EXPIRE_DATE * 3600 : EXPIRE_DATE * 3600, JsonUtil.toJson(loginUser));

        return loginUser;
    }

}
