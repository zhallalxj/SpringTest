package org.zh.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Description:TODO(  WebContextUtil 工具类 )   
 * @author: level.meng 
 * @date:   2017年2月20日 下午2:26:56   
 *     
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class WebContextUtil {
	
	private static ThreadLocal<WebContextUtil> tlv = new ThreadLocal<WebContextUtil>();  
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext servletContext;
  
    protected WebContextUtil() {  
    }  
  
    public HttpServletRequest getRequest() {
        return request;  
    }  
  
    public void setRequest(HttpServletRequest request) {
        this.request = request;  
    }  
  
    public HttpServletResponse getResponse() {
        return response;  
    }  
  
    public void setResponse(HttpServletResponse response) {
        this.response = response;  
    }  
  
    public ServletContext getServletContext() {
        return servletContext;  
    }  
  
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;  
    }  
  
    private WebContextUtil(HttpServletRequest request,
                           HttpServletResponse response, ServletContext servletContext) {
        this.request = request;  
        this.response = response;  
        this.servletContext = servletContext;  
    }  
  
    public static WebContextUtil getInstance() {  
        return tlv.get();  
    }  
  
    public static void create(HttpServletRequest request,
                              HttpServletResponse response, ServletContext servletContext) {
    	WebContextUtil wc = new WebContextUtil(request, response, servletContext);  
        tlv.set(wc);  
    }  
  
    public static void clear() {  
        tlv.set(null);  
    }  

}
