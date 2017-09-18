package org.zh.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZhaoHang
 */
public class CommonUtil {

    public static String generatorCookie(String key, String value, String expireDate, String tokenDomain, String tokenPath, boolean... isHttpOnly) {
        StringBuilder cookie = new StringBuilder().append(key).append("=").append(value)
                .append("; Domain=").append(tokenDomain).append("; Expires=").append(expireDate)
                .append("; Path=").append(tokenPath);

        if (isHttpOnly.length == 1 && isHttpOnly[0]) cookie.append("; HTTPOnly");
        return cookie.toString();
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-Ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        int commaOffset = ip.indexOf(',');
        if (commaOffset < 0) {
            return ip;
        }
        return ip.substring(0, commaOffset);
    }

}
