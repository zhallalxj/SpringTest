package org.zh.auth;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.zh.Exception.AuthorizationException;
import org.zh.constants.CommonConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Li ShaoQing
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
    AuthorizationTokenValidate authorizationTokenValidate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("Authentication");
        if (!(handler instanceof HandlerMethod))
            return true; // not HandlerMethod return. eg. js css or other request url

        HandlerMethod handler2 = (HandlerMethod) handler;
        Authentication authentication = handler2.getMethodAnnotation(Authentication.class);



        if (null == authentication)  return true;             //没有声明权限,放行

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            t(request, response,handler2);
            return false;
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if (CommonConstants.AUTH_TOKEN.equals(cookie.getName())){
                token = cookie.getValue();
                break;
            }
        }
        if (token == null) {
            t(request, response,handler2);
            return false;
        }

        try {
            authorizationTokenValidate.validate(token);
        } catch (AuthorizationException e) {
            //  验证失败 跳转到登录页面。根据不同的验证失败异常，可做一些个性化处理
            for (Cookie cookie : cookies) {
                Cookie c = new Cookie(cookie.getName(), cookie.getValue());
                c.setMaxAge(0);
                c.setPath("/");
                c.setDomain("localhost");
                c.setHttpOnly(true);
                response.addCookie(c);
            }
            response.sendRedirect(redirectUrl(request.getRequestURI()));
            return false;
        }

        return true;
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
        if (null == responseBody)  isResponseBody =  false;

        if(!isResponseBody){
			/*非ajax 请求处理*/
            response.sendRedirect(redirectUrl(request.getRequestURI()));
        }else{
        	/*ajax 请求处理*/
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");

            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("code",100010);
            resultMap.put("message","权限不足");

            response.getWriter().write(new Gson().toJson(resultMap));
            response.getWriter().close();

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
