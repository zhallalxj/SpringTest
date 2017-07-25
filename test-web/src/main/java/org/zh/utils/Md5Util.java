package org.zh.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.MessageDigest;

/**
 * Created by sundy on 2015/1/19
 *
 * @version $Id: Md5Util.java 855 2015-05-19 04:24:28Z hsj $
 * @since 1.0
 */
public class Md5Util {
    /**
     * MD5编码
     *
     * @param _source
     * @return
     */
    public static String encodeByMD5(String _source) {
        try {
            byte[] bytes = MessageDigest.getInstance("MD5").digest(
                    _source.getBytes());
            char[] chars = new char[bytes.length * 2];

            for (int i = 0; i < bytes.length; i++) {
                chars[i * 2] = hexDigits[bytes[i] >>> 4 & 0xf]; // 转换高 4位,
                chars[i * 2 + 1] = hexDigits[bytes[i] & 0xf]; // 转换低 4位
            }

            return new String(chars);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Base64编码
     *
     * @param _source
     * @return
     */
    public static String encodeByBase64(String _source) {
        try {
            return new BASE64Encoder().encode(_source.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Base64解码
     *
     * @param _source
     * @return
     */
    public static byte[] decodeByBase64(String _source) {
        try {
            return new BASE64Decoder().decodeBuffer(_source);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String encodeBySHA1(String _source) {
        try {
            byte[] bytes = MessageDigest.getInstance("MD5").digest(
                    _source.getBytes());
            char[] chars = new char[bytes.length * 2];

            for (int i = 0; i < bytes.length; i++) {
                chars[i * 2] = hexDigits[bytes[i] >>> 4 & 0xf]; // 转换高 4位,
                chars[i * 2 + 1] = hexDigits[bytes[i] & 0xf]; // 转换低 4位
            }

            return new String(chars);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 16进制位字符
     */
    private final static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}
