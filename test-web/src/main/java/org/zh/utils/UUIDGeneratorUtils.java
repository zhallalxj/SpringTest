package org.zh.utils;

import java.util.UUID;

public class UUIDGeneratorUtils {
	
	public static String buildUUID(){
        UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 去掉"-"符号  
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
	}

    public static void main(String[] args) {
        System.out.println(UUIDGeneratorUtils.buildUUID());
        System.out.println(Md5Util.encodeByMD5(UUIDGeneratorUtils.buildUUID()));
    }

}
