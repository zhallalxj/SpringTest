package org.zh.constants;

import org.zh.utils.PropertiesFileUtil;

/**
 * Created with IntelliJ IDEA 2017.1.5. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/8/31  <br/>
 * Time: 15:59  <br/>
 *
 * @Description: 描述本类的作用
 */
public class CommonConstants {

    private static PropertiesFileUtil propertiesFileUtil = PropertiesFileUtil.getInstance("setting");

    public static final Integer EXPIRE_DATE = Integer.valueOf(propertiesFileUtil.get("token.expire.date"));

    public static final Integer REMEMBER_ME_EXPIRE_DATE = Integer.valueOf(propertiesFileUtil.get("token.rememberMe.expire.date"));

    public static final String AUTH_TOKEN = "U_AUTH";

}
