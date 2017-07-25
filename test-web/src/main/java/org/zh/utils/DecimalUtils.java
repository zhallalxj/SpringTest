package org.zh.utils;

import java.text.DecimalFormat;

public class DecimalUtils {

	
	/**
	 *  DOUBLE 保留两位小数
	 * @param d
	 * @return
	 */
	
	public static String formatDouble(Object d) {
        DecimalFormat df = new DecimalFormat("#.00");     
        return df.format(d);
    }
	
}
