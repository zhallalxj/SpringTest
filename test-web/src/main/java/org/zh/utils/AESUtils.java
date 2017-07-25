package org.zh.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>
 *     参数加密解密工具，不要和DESEncrypt弄混了
 * </p>
 *
 * @author wushige
 * @date 2016-05-13 19:46
 */
public class AESUtils {

    private static final String AES = "AES";

    private static final String CRYPT_KEY = "y2W89L6BkRAFljhN";

    /**
     * 加密
     *
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AES);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);//设置密钥和加密形式
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AES);//设置加密Key
        cipher.init(Cipher.DECRYPT_MODE, keySpec);//设置密钥和解密形式
        return cipher.doFinal(src);
    }

    /**
     * 二行制转十六进制字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String str;
        for (byte aB : b) {
            str = (Integer.toHexString(aB & 0XFF));
            if (str.length() == 1)
                hs = hs + "0" + str;
            else
                hs = hs + str;
        }
        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) {
        try {
            return new String(decrypt(hex2byte(data.getBytes()), CRYPT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) {
        try {
            return byte2hex(encrypt(data.getBytes(), CRYPT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {


        String a = "1234567";


        String b = encrypt(a);


        System.out.println("加密结果："+b);


        String c = decrypt(b);


        System.out.println(c);

    }

}
