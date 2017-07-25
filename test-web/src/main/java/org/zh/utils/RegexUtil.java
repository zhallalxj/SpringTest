package org.zh.utils;

import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sundy on 2015/4/8
 *
 * @version $Id: RegexUtil.java 855 2015-05-19 04:24:28Z hsj $
 * @since 1.0
 */
public class RegexUtil {


    /**
     * 验证是否是电话号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 验证是否是邮箱
     *
     * @param mail
     * @return
     */
    public static boolean isMail(String mail) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(mail);
        return m.matches();
    }

    /**
     * 验证是否是身份证
     *
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        Assert.notNull(idCard);
        int length = idCard.length();
        Pattern p;
        if (length == 15) {
            p = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
        } else if (length == 18) {
            p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        } else {
            return false;
        }
        Matcher m = p.matcher(idCard);
        return m.matches();
    }
}
