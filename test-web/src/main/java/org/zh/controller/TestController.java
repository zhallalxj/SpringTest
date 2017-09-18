package org.zh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zh.bean.CustomUserDetails;
import org.zh.bean.User;
import org.zh.bean.UserExample;
import org.zh.constants.CommonConstants;
import org.zh.exception.AccountNotExistException;
import org.zh.exception.TokenExpiredException;
import org.zh.exception.UserNotLoginException;
import org.zh.exception.WrongPasswordException;
import org.zh.service.IUserService;
import org.zh.utils.BCryptUtil;
import org.zh.utils.CustomResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 测试Controller
 * Created by ZhaoHang on 2017/7/25.
 */
@Controller
@Api(value = "测试", description = "测试")
@RequestMapping(value = "/")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager userAuthenticationManager;

    @ResponseBody
    @RequestMapping(value = "login_ajax", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        CustomResponse customResponse = new CustomResponse();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
        CustomUserDetails userDetails;
        try {
            Authentication authentication = userAuthenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        } catch (AuthenticationException e) {
            if (e instanceof UsernameNotFoundException) {
                LOGGER.debug(e.toString());
                return customResponse.getErrorJson("用户不存在", 10001);
            }
            if (e instanceof BadCredentialsException) {
                LOGGER.debug(e.toString());
                return customResponse.getErrorJson("用户密码不正确", 10001);
            }
            LOGGER.debug(e.toString());
            return customResponse.getErrorJson("用户名或密码不正确", 10001);
        }


        return customResponse.getSuccessJson("登录成功");
    }

    @ResponseBody
    @RequestMapping(value = "/loginToken", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=utf-8")
    @ApiOperation(value = "登录", notes = "登录")
    public String loginToken(@RequestParam String userName,
                             @RequestParam String password,
                             @RequestParam(value = "rememberMe", required = false) boolean rememberMe,
                             HttpServletResponse response) {
        CustomResponse customResponse = new CustomResponse();


        try {
            User user = userService.login(userName, password, rememberMe);
            customResponse.addValue("userName", user.getUserName());
            customResponse.addValue("token", user.getToken());

            /*response.addHeader("Set-Cookie", CommonUtil.generatorCookie(CommonConstants.AUTH_TOKEN, user.getToken(),
                    user.getExpireDate().toString(),
                    propertiesFileUtil.get("token.domain"), propertiesFileUtil.get("token.path")));*/
        } catch (AccountNotExistException e) {
            return customResponse.getErrorJson("用户不存在");
        } catch (WrongPasswordException e) {
            return customResponse.getErrorJson("密码错误");
        }


        return customResponse.getSuccessJson("登录成功");
    }

    @ResponseBody
    @RequestMapping(value = "/getUserInfo", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=utf-8")
    @org.zh.auth.Authentication
    @ApiOperation(value = "查询", notes = "查询")
    public String getUserInfo(HttpServletRequest request) {
        CustomResponse customResponse = new CustomResponse();

        User user;
        try {
            user = userService.searchUser(request);
        } catch (UserNotLoginException e) {
            return customResponse.getErrorJson(e.getMessage(),e.getErrorCode());
        } catch (AccountNotExistException | TokenExpiredException e) {
            return customResponse.getErrorJson(e.getMessage());
        }

        customResponse.addValue("user", user);

        return customResponse.toJSONString();
    }



    private String searchAuthToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (CommonConstants.AUTH_TOKEN.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(@RequestParam String username,
                           @RequestParam String password, Model model) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(username);
        User user = userService.selectFirstByExample(userExample);
        if (user != null) {
            model.addAttribute("message", "用户已存在");
        } else {
            user = new User();
            user.setUserName(username);
            user.setPassword(BCryptUtil.BCryptEncode(password));
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userService.insertSelective(user);

            model.addAttribute("message", "注册成功");
        }


        return "/signin";
    }

}
