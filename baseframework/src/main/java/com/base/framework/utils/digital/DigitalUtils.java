package com.base.framework.utils.digital;


import java.math.BigDecimal;

public class DigitalUtils {

	
	/**
	 * 对数字字符串进行四舍五入处理
	 * 
	 * @param str 处理参数
	 * @param scale 保留小数位数
	 * @return 返回值
	 */
	public static String RoundOf(String str, int scale) {
		// 输入精度小于0则抛出异常
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		// 取得数值
		BigDecimal b = new BigDecimal(str);
		// 取得数值1
		BigDecimal one = new BigDecimal("1");
		// 原始值除以1，保留scale位小数，进行四舍五入
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * 对数字进行四舍五入处理
	 * 
	 * @param str 处理参数
	 * @param num 保留小数位数
	 * @param isMakeup 如果小数点后面的位数小于要保留小数点位数，是否补0
	 * @return 返回值
	 */
	public static String RoundOf(String str, int num, boolean isMakeup) {

		// 对数字字符串进行四舍五入处理
		str = RoundOf(str, num);

		// 取得小数点后面的数字字符串
		String str1 = str.substring(str.indexOf(".") + 1, str.length());
		// 如果小数点后面的位数小于要保留小数点位数
		if (str1.length() < num) {
			if (isMakeup) {
				for (int n = 0; n < (num - str1.length()); n++) {
					str = str + "0";
				}
			}
		}

		return str;
	}
	
}
