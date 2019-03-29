package tv.zhangjia.bike;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		
		System.out.println(now.getSeconds());
		String registerTime = sdf.format(now);
		System.out.println(registerTime);
	}
}
