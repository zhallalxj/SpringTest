package org.zh.security.handler;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @Description:TODO(RequestMatcher Ajax 类型判断)   
 * @author: level.meng 
 * @date:   2017年2月16日 下午4:55:26   
 *     
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class NonAjaxRequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {
        return !"XmlHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}