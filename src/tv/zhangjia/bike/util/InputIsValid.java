package tv.zhangjia.bike.util;

public class InputIsValid {
	public boolean isNumber(String str) {
		if(str.matches("^[0-9]*$")) {
			return true;
		} else {
			return false;
		}
	}
	
}
