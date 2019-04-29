package tv.zhangjia.bike.util;

import java.math.BigInteger;
import java.util.Scanner;

public class InputIsValid {
	public static void main(String[] args) {
		while (true) {
			Scanner input = new Scanner(System.in);
			System.out.print("请输入：");
			String str = input.next();

			if (new InputIsValid().isInt(str)) {
				// if (str.matches("^[0-9]*$") && str.matches("^\\d{m,n}$")) {
				System.out.println("合法");
			} else {
				System.out.println("不合法");
			}

		}
	}

	/**
	 * 输入是否是Int类型的正整数 和 0
	 * @param str 要判断的字符串
	 * @return 合法返回true，不合法返回false
	 */
	public boolean isNumber(String str) {
		if (str.matches("^[0-9]*$")) {
			BigInteger l = new BigInteger(str);
			BigInteger n = new BigInteger("32767");
			// 如果输入的超过Int类型
			if (l.compareTo(n) > 0) {
				return false;
			}
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 输入是否是Int类型的正整数，不包括0
	 * @param str 要判断的字符串
	 * @return 合法返回true，不合法返回false
	 */
	public boolean isPositiveInteger(String str) {
		if (str.matches("^[1-9]*$")) {
			BigInteger l = new BigInteger(str);
			BigInteger n = new BigInteger("32767");
			// 如果输入的超过Int类型
			if (l.compareTo(n) > 0) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 输入是否是Int类型的整数（负整数，正整数，0）
	 * @param str 要判断的字符串
	 * @return 合法返回true，不合法返回false
	 */
	public boolean isInt(String str) {
		if (str.matches("^-?[0-9]\\d*$")) {
			BigInteger l = new BigInteger(str);
			BigInteger n = new BigInteger("32767");
			BigInteger m = new BigInteger("-32768");
			// 如果输入的超过Int类型
			if (l.compareTo(n) > 0  || l.compareTo(m) < 0 ) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 输入的是否是正整数或者正double
	 * 
	 * @param str 要判断的字符串
	 * @return 合法返回true，不合法返回false
	 */
	public boolean isDouble(String str) {
		if (str.matches("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|[1-9]\\d*$")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断输入的手机号是否合法
	 * @param str 要判断的手机号
	 * @return 合法返回true，不合法返回false
	 */
	public boolean isTrueTel(String str) {
		if (str.matches( "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")) {
			return true;
		} else {
			return false;
		}
	}

}
