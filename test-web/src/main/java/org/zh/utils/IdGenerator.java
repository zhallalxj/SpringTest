package org.zh.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.net.InetAddress;
import java.util.Random;
import java.util.UUID;

/**
 * @author
 */
public class IdGenerator {
    private static final int IP;

    static {
        int ipadd;
        try {
            ipadd = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    private static short counter = (short) 0;
    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);
    private static String sep = "";

    public static String getSeparator() {
        return sep;
    }

    public void setSeparator(String separator) {
        sep = (separator == null ? "" : separator);
    }

    public static String generate() {
        return new StringBuffer(36)
//		.append( format( getIP() ) ).append(sep)
//		.append( format( getJVM() ) ).append(sep)
                .append(format(getHiTime())).append(sep)
                .append(format(getLoTime())).append(sep)
                .append(format(getCount()))
                .toString().toUpperCase();
    }

    /**
     * Unique across JVMs on this machine (unless they load this class
     * in the same quater second - very unlikely)
     */
    protected static int getJVM() {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there
     * are > Short.MAX_VALUE instances created in a millisecond)
     */
    protected static short getCount() {
        synchronized (IdGenerator.class) {
            if (counter < 0) counter = 0;
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
    protected static int getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    protected static short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    protected static int getLoTime() {
        return (int) System.currentTimeMillis();
    }

    private static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    protected static String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected static String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }


    private static int id = 0;

    private static Object obj = new Object();

    /**
     * 获得唯一id
     *
     * @return id
     */
    public static long getId() {
        return getRandomLong(16);
    }


    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};


    /**
     * 获取8位标识
     *
     * @return
     */
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    /**
     * 去除0,1,o,l,O,L
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
        Random rd = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = rd.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


    public static Long getRandomLong(int length) {
        String str = "0123456789";
        Random rd = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <=length; i++) {
            int number = rd.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return NumberUtils.toLong(sb.toString());
    }

    public static Long getRandomLong() {

        Random random = new Random();

        int num = random.nextInt(899999);

        int result = num+100000;

        return NumberUtils.toLong(result+"");
    }


//    /**
//     * 生成短信验证码
//     *
//     * @return
//     */
//    public static String generateVerifyCode() {
//        String[] beforeShuffle = new String[]{"1", "2", "3", "4", "5", "6", "7",
//                "8", "9", "0"};
//        List list = Arrays.asList(beforeShuffle);
//        Collections.shuffle(list);
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < 4; i++) {
//            sb.append(list.get(i));
//        }
//        return sb.toString();
//    }
public static void main(String[] args){
    System.out.print(generateShortUuid());
}
}
