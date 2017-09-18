package org.zh.auth;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.zh.constants.CommonConstants;
import org.zh.exception.AccountNotExistException;
import org.zh.exception.TokenExpiredException;
import org.zh.exception.UserNotLoginException;
import org.zh.service.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhaoHang
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    /*----------------- example ---------
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <bean class="com.yjdgis.interceptors.MyInterceptor1"></bean>
        </mvc:interceptor>

　　  <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <bean class="com.yjdgis.interceptors.MyInterceptor2"></bean>
         </mvc:interceptor>
    </mvc:interceptors>
    ---------------------------------*/

    @Autowired
    private AuthorizationTokenValidate authorizationTokenValidate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        logger.debug("Authentication");
        if (!(handler instanceof HandlerMethod))
            return true; // not HandlerMethod return. eg. js css or other request url

        HandlerMethod handler2 = (HandlerMethod) handler;
        Authentication authentication = handler2.getMethodAnnotation(Authentication.class);

        if (null == authentication) return true;   //没有声明认证,放行


        String token = request.getParameter(CommonConstants.AUTH_TOKEN);

        if (null != token) {
            try {
                authorizationTokenValidate.validate(token);
                return true;
            } catch (AccountNotExistException e) {
                writeMessage(response, 10010, "用户账户不存在");
                return false;
            } catch (TokenExpiredException e) {
                writeMessage(response, 10010, "用户认证失效");
                return false;
            }
        }

        try {
            UserDetails.getCustomUserDetails();
        } catch (UserNotLoginException e) {
            t(request, response, handler2);
            return false;
        }

        return true;
    }

    private void writeMessage(HttpServletResponse response, int errorCode, String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", errorCode);
        resultMap.put("message", message);

        response.getWriter().write(new Gson().toJson(resultMap));
        response.getWriter().close();
    }

    private String redirectUrl(String requestURI) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("/login").append(URLEncoder.encode(requestURI, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("wrong URLEncoder:", e);
            return "/login";
        }
        return stringBuilder.toString();
    }

    private void t(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler2) throws IOException {

        boolean isAjax = isAjaxRequest(request);
        ResponseBody responseBody = handler2.getMethodAnnotation(ResponseBody.class);
        boolean isResponseBody = true;
        if (null == responseBody) isResponseBody = false;

        if (!isResponseBody) {
            /*非ajax 请求处理*/
            response.sendRedirect(redirectUrl(request.getRequestURI()));
        } else {
            /*ajax 请求处理*/
            writeMessage(response, 10010, "请登录后再操作");

        }
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
    }

}
