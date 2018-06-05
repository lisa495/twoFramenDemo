/*
 * 作者		刘国山 (刘国山)
 * 开发环境	WindowsXp MyEclipse6.5 JDK1.6.0_22
 * 开发日期	2011-4-15
 */
package com.base.framework.utils.regular;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 */
public abstract class ValidateTools {

	 // 正则,匹配URL
	private static final String REGEX_URL = "http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";

	 // 正则,匹配全汉字
	private static final String REGEX_CHINESE_ONLY = "[\\u4e00-\\u9fa5]+";

	 // 正则,匹配a-z(英文小写)
	private static final String REGEX_LOWERCASE_ONLY = "[a-z]+";

	 // 正则,匹配A-Z(英文大写)
	private static final String REGEX_UPPERCASE_ONLY = "[A-Z]+";

	 // 正则,匹配a-z A-Z(英文大写或小写)
	private static final String REGEX_LowerOrUpperCase = "[A-Za-z]+";

	 // 正则,匹配0-9(数字)
	private static final String REGEX_NUM_ONLY = "[0-9]+";

	 // 正则,匹配0和非0开头的数字
	private static final String REGEX_ZERO_OR_NOTSTARTWITHZERO = "(0|[1-9][0-9]*)";

	 // 正则,只能由a-z A-Z 0-9 组成
	private static final String REGEX_AZ_az_Num_DOWNLINE = "[a-zA-Z0-9]+";

	 // 正则,匹配必须是英文字母开头,只能由a-z A-Z 0-9 和 下划线"_"组成
	private static final String REGEX_AZ_az_Num_DOWNLINE_STARTWITHENGLISH = "[a-zA-Z][a-zA-Z0-9_]+";

	 // 正则,匹配非特殊字符（包含-#空格#）
	private static final String REGEX_HAVE_SOECIAL_CHAR = "^[\\u4E00-\\u9FA5A-Za-z0-9\\\\-\\\\#\\\\s]+";

	 // 正则,匹配邮箱
	private static final String REGEX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

	 // 正则,匹配手机号
	private static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

	private static final String REGEX_EMIAL_NEW="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
	private static final String REGEX_EMAIL_RS = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	private static final String REFORM_CHECK_PHONE_NUMBER="^((13[0-9])|(15[0-9])|(18[0-9])|(14[0-9])|(17[0-9]))[0-9]{8}$";

	private static boolean matcher(String string, String regex) {
		if (string == null) {
			return false;
		}
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(string);
		return m.matches();
	}
	
	//==============================================================
	
	/**
	 * 是否为Url 是:返回true 否:返回false
	 */
	public static boolean isUrl(String string) {
		return matcher(string, REGEX_URL);
	}

	/**
	 * 验证是否只为汉字 是:返回true 否:返回false
	 */
	public static boolean isChineseOnly(String string) {
		return matcher(string, REGEX_CHINESE_ONLY);
	}

	/**
	 * 验证是否只为英文小写(a-z) 是:返回true 否:返回false
	 */
	public static boolean isLowerCaseOnly(String string) {
		return matcher(string, REGEX_LOWERCASE_ONLY);
	}

	/**
	 * 验证是否只为英文大写(A-Z) 是:返回true 否:返回false
	 */
	public static boolean isUpperCaseOnly(String string) {
		return matcher(string, REGEX_UPPERCASE_ONLY);
	}

	/**
	 * 验证是否只为英文(A-Z a-z) 是:返回true 否:返回false
	 */
	public static boolean isLowerOrUpperCase(String string) {
		return matcher(string, REGEX_LowerOrUpperCase);
	}

	/**
	 * 验证是否为Email 是:返回true 否:返回false
	 */
	public static boolean isEmail(String string) {
		return matcher(string, REGEX_EMAIL);
	}
	public static boolean isEmailNew(String string){
		return matcher(string,REGEX_EMAIL_RS);
	}

	/**
	 * 验证是否只为数字(0~9) 是:返回true 否:返回false
	 */
	public static boolean isNumOnly(String string) {
		return matcher(string, REGEX_NUM_ONLY);
	}

	/**
	 * 验证是否为0或者不为0开头的数字 是:返回true 否:返回false
	 */
	public static boolean isZeroOrNotStartWithZeroNum(String string) {
		return matcher(string, REGEX_ZERO_OR_NOTSTARTWITHZERO);
	}

	/**
	 * 验证只能由A-Z a-z 0-9组成
	 */
	public static boolean isAzazNum(String string) {
		return matcher(string, REGEX_AZ_az_Num_DOWNLINE);
	}

	/**
	 * 验证是否为由A-Z a-z 0-9 和 下划线"_"组成并且是字母开头的字符串 是:返回true 否:返回false
	 */
	public static boolean isAzazNumDownLineAndStartWithEnglish(String string) {
		return matcher(string, REGEX_AZ_az_Num_DOWNLINE_STARTWITHENGLISH);
	}

	/**
	 * 验证是否含有特殊的字符（－＃空格除外）
	 */
	public static boolean isHasSoecialChar(String string) {
		return !matcher(string, REGEX_HAVE_SOECIAL_CHAR);
	}

	/**
	 * 验证是否匹配手机号 是:返回true 否:返回false
	 */
	public static boolean isMobile(String string) {
		return matcher(string, REGEX_MOBILE);
	}

	public static  boolean isMobileNumber(String string){
		if(TextUtils.isEmpty(string)||replaceAllSpace(string).length()!=11){
			return false;
		}
		return isNumOnly(string);
	}
	public static  boolean isReform302MobileNumber(String string){
		if(TextUtils.isEmpty(string)||replaceAllSpace(string).length()!=11){
			return false;
		}
		return matcher(replaceAllSpace(string), REFORM_CHECK_PHONE_NUMBER);
	}


	public static String replaceAllSpace(String value){
		if(TextUtils.isEmpty(value)){
			return "";
		}
		return value.trim().replaceAll(" ","");
	}
	private static boolean matcherRegisterPass(String value){
		if(TextUtils.isEmpty(value))
			return false;
		return value.matches("(^[a-zA-Z0-9]{8,20}+$)");
	}
	private static boolean isSoEasy(String value){
		if(TextUtils.isEmpty(value))
			return true;
		boolean result=true;
		for (int i = 1; i < value.length(); i++) {
			if (!String.valueOf(value.charAt(i-1)).equalsIgnoreCase(String.valueOf(value.charAt(i)))) {
				result=false;
				break;
			}
		}
		return result;
	}
	public static boolean matcherLoginPass(String value){
		return matcherRegisterPass(value)&&!isSoEasy(value);
	}

	
}
