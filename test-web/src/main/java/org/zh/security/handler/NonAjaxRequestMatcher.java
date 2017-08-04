package org.zh.security.handler;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class NonAjaxRequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {
        return !"XmlHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}