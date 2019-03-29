package tv.zhangjia.bike;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		System.out.println(c.getTime());
		c.add(Calendar.MONTH, 12);// 日期加3个月
		System.out.println(c.getTime());
	}
}
