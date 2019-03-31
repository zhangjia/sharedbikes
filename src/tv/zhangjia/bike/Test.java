package tv.zhangjia.bike;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static  List<String> arr = new ArrayList<>();
	static {
		arr.add("1");
		arr.add("b");
		arr.add("c");
		arr.add("11");
	}
	public static void main(String[] args) {
		System.out.println(new Test().f("1",0));
	}
	
	public String f(String username, int i) {
		
		String newUsername = username +(++i);
		if(istrue(newUsername)) {
//			newUsername = f(username,i); //12
			 f(username,i);//11
		}
		return newUsername;
		
		
			
	}
	
	
	public boolean istrue(String u) {
		for (String string : arr) {
			if(string.equals(u)) {
				return true;
			}
		}
		
		return false;
	}
}
