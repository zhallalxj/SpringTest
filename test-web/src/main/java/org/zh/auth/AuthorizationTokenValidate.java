package org.zh.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zh.bean.User;
import org.zh.exception.AccountNotExistException;
import org.zh.exception.TokenExpiredException;
import org.zh.service.IUserService;
import org.zh.utils.DateFormatUtil;
import org.zh.utils.JsonUtil;
import org.zh.utils.RedisUtil;

import java.util.Date;

/**
 * @author ZhaoHang
 */
@Component
public class AuthorizationTokenValidate {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationTokenValidate.class);

    @Autowired
    private IUserService userService;

    public User validate(String token) throws AccountNotExistException, TokenExpiredException {
        User user = userService.searchByToken(token);
        if (user == null) {
            logger.warn("用户不存在，Token = {}:", token);
            throw new AccountNotExistException("用户不存在");
        }

        Date currentDate = new Date();
        if (currentDate.after(user.getExpireDate())) {
            logger.warn("TOKEN失效，Token = {}", token);
            RedisUtil.del(token);
            throw new TokenExpiredException("TOKEN失效");
        }

        Date visitDate = user.getVisitDate();
        Date now = new Date();

        if (visitDate == null || !DateFormatUtil.isSameDay(visitDate, now)) {


            user.setVisitDate(now);
            user.setLoginDate(now);
            userService.updateByPrimaryKey(user);
            RedisUtil.updateData(token, JsonUtil.toJson(user));
        }

        return user;
    }
}
