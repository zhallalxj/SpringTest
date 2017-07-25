package org.zh.utils;

import java.util.Arrays;

/**
 * 
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: level.meng 
 * @date:   2017年3月3日 下午5:22:02   
 *     
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ArraysUtils {
	
	/**
	 *  Arrays.binarySearch()方法只能用于有序数组！！！如果数组无序的话得到的结果就会很奇怪。
	 * @param coinDealType Object 数组有序
	 * @param targetValue
	 * @return
	 */
	public  static boolean ArraysBinarySearchInt(int[] coinDealType, int targetValue) {

		Arrays.sort(coinDealType);
		int a = Arrays.binarySearch(coinDealType, targetValue);
		
		if (a > 0){
			return true;
		}else{
			return false;
		}

	}
}

