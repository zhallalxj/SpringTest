package org.zh.security.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by relieved on 2017/6/1.
 */
public class SimpleLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(SimpleLoginSuccessHandler.class);
    private String defaultTargetUrl;
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
       /* String session_id = request.getSession().getId();
        // new一个Cookie对象,键值对为参数
        Cookie cookie = new Cookie("JSESSIONID", session_id);

        int outTime = 60 * 30 * 4;

        // tomcat下多应用共享
        cookie.setPath("/open");
        cookie.setMaxAge(outTime);
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        // 将Cookie添加到Response中,使之生效
        Cookie cookieWeb = new Cookie("JSESSIONID", session_id); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        // tomcat下多应用共享
        cookieWeb.setPath("/web");
        cookieWeb.setMaxAge(outTime);
        response.addCookie(cookieWeb); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        Cookie cookieTopic = new Cookie("JSESSIONID", session_id); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        // tomcat下多应用共享
        cookieTopic.setPath("/topic");
        cookieTopic.setMaxAge(outTime);
        response.addCookie(cookieTopic); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
*/

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            System.out.println("savedRequest is null ");
            //用户判断是否要使用上次通过session里缓存的回调URL地址  
            int flag = 0;
            //通过提交登录请求传递需要回调的URL callCustomRediretUrl  
            if (request.getSession().getAttribute("callCustomRediretUrl") != null
                    && !"".equals(request.getSession().getAttribute("callCustomRediretUrl"))) {
                String url = String.valueOf(request.getSession().getAttribute("callCustomRediretUrl"));
                //若session 存在则需要使用自定义回调的URL 而不是缓存的URL  
                super.setDefaultTargetUrl(url);
                super.setAlwaysUseDefaultTargetUrl(true);
                flag = 1;
                request.getSession().setAttribute("callCustomRediretUrl", "");
            }
            //重设置默认URL为主页地址  
            if (flag == 0) {
                super.setDefaultTargetUrl(defaultTargetUrl);
            }
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }
        //targetUrlParameter 是否存在  
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.setAlwaysUseDefaultTargetUrl(false);
            super.setDefaultTargetUrl("/");
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        //清除属性  
        clearAuthenticationAttributes(request);
        // Use the DefaultSavedRequest URL  
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        if (targetUrl != null && "".equals(targetUrl)) {
            targetUrl = defaultTargetUrl;
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }/**/

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    @Override
    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }
}
