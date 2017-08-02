package org.zh.security.handler;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @Description:TODO(权限不足处理结果)   
 * @author: level.meng 
 * @date:   2017年2月16日 下午4:55:03   
 *     
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class AdminAccessDeniedHandler implements AccessDeniedHandler {

	@SuppressWarnings("static-access")
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException,ServletException {
		 
		boolean isAjax = isAjaxRequest(request);
		
		if(!isAjax){
			/*非ajax 请求处理*/
			response.sendRedirect(request.getContextPath() + getAccessDeniedUrl());
			String deniedMessage = accessDeniedException.getMessage();
			request.getSession().setAttribute("ACCESS_DENIED_MSG", deniedMessage);
        }else{
        	/*ajax 请求处理*/
        	 response.setCharacterEncoding("UTF-8");
             response.setContentType("text/plain");
             response.getWriter().write("权限不足");
             response.getWriter().close();
        	 
        }
	}
	
	public String getAccessDeniedUrl() {
		return accessDeniedUrl;
	}

	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}

	private String accessDeniedUrl = null;
	
	private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
    }

}
