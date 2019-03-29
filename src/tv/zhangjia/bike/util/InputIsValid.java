package tv.zhangjia.bike.util;

import java.util.Scanner;

public class InputIsValid {
	public static void main(String[] args) {
		while(true) {
			Scanner input = new Scanner(System.in);
			System.out.print("请输入：");
			String str = input.next();
			
			if(str.matches("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|[1-9]\\d*$")) {
				System.out.println("合法");
			} else {
				System.out.println("不合法");
			}
		
		}
	}
	/**
	 * 输入是否是数字
	 * @param str
	 * @return
	 */
	public boolean isNumber(String str) {
		if(str.matches("^[0-9]*$")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 输入的是否是正整数或者正double
	 * @param str
	 * @return
	 */
	public boolean isDouble(String str) {
		if(str.matches("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|[1-9]\\d*$")) {
			return true;
		} else {
			return false;
		}
	}
	
}
