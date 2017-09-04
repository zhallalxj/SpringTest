package org.zh.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zh.Exception.AuthorizationException;
import org.zh.constants.CommonConstants;

import java.util.Date;

/**
 * @author Li ShaoQing
 */
@Component
public class AuthorizationTokenValidate {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationTokenValidate.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserLoginLogService userLoginLogService;

    @Transactional(readOnly = false)
    public User validate(String token) throws AuthorizationException {
        User user = accountService.searchByToken(token);
        if (user == null) {
            logger.warn("account not exist. search by token:" + token);
            throw new AccountNotExistException("account not exist");
        }

        Date currentDate = new Date();
        if (currentDate.after(user.getExpireDate())) {
            logger.warn("token expired. token:" + token);
            redisService.delTokenFromRedis(token);
            throw new TokenExpiredException("token expired");
        }

        Date visitDate = user.getVisitDate();
        Date now = new Date();

        if(visitDate == null || !DateFormatUtil.isSameDay(visitDate, now)) {
            UserLoginLog log = new UserLoginLog();
            log.setAccount(user.getLoginAccount());
            log.setOperateType(CommonConstants.LOG_OPERATOR_TYPE_VISIT);
            log.setIp(user.getLoginIp());
            log.setOrigin(user.getOrigin());
            userLoginLogService.log(log);

            user.setVisitDate(now);
            user.setLoginDate(now);
            accountService.updateUser(user);
            redisService.updateTokenToRedis(token, JsonUtil.toJson(user));
        }

        return user;
    }
}
