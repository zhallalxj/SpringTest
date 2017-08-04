package org.zh.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @Description:TODO( BCrypt 加解密工具类)   
 * @author: level.meng 
 * @date:   2017年2月19日 下午5:54:36   
 *     
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class BCryptUtil {
	
	/*
	 *  
	 */
	public static String BCryptEncode(String content){
		
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		String bcryptPassword = bcryptPasswordEncoder.encode(content);

		return bcryptPassword;
		
	}

}
