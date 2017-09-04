package org.zh.auth;


/**
 * Created with IntelliJ IDEA 2017.1.5. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/8/31  <br/>
 * Time: 14:39  <br/>
 *
 * @Description: Token生成
 */
public class AuthTokenGenerate {

    public static String generateToken(String account, String salt) {
        String input = account + System.nanoTime();
        byte[] token = Digests.sha1(input.getBytes(), salt.getBytes(), 1024);
        return Encodes.encodeHex(token);
    }

    public static String generateEmailBindingToken(String account, String email) {
        String input = account + System.nanoTime();
        byte[] token = Digests.sha1(input.getBytes(), email.getBytes(), 1024);
        return Encodes.encodeHex(token);
    }
}
