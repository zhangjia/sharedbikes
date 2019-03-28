package tv.zhangjia.bike.data;

import java.util.ArrayList;
import java.util.List;

import tv.zhangjia.bike.entity.Bike;
import tv.zhangjia.bike.entity.User;
/**
 * 模拟数据库
 * @ProjectName	SharedBikes	  
 * @PackgeName	tv.zhangjia.bike.data	
 * @ClassName	Database	
 * @author	ZhangJia
 * @Version	V1.0
 * @date	2019年3月25日 下午6:31:16
 */
public class Database {
	public static final List<User> USERS = new ArrayList<>();
	public static final List<Bike> BIKES = new ArrayList<>();
	static {
		//向数据库中默认添加一个管理员
			USERS.add(new User(1,"a","a","15628",true,10,0,0,"2019-03-25"));
		//向数据库中默认添加一个数据库
			USERS.add(new User(2,"b","b","15629",false,10,0,0,"2019-03-25"));
			
			BIKES.add((new Bike(1,"脚蹬车",0.1,"创意大厦",1,0,"二维码")));
			BIKES.add((new Bike(2,"助力车",0.2,"绿色家园",1,0,"二维码")));
	}
	
	/**
	 * 生成下一个用户的ID
	 * @return 下一个用户的ID
	 */
	public static int nextUserId() {
		if(USERS.isEmpty()) {
			return 1;
		}
		return USERS.get(USERS.size() - 1).getId() + 1;
	}
	
	/**
	 * 生成下一个Bike的ID
	 * @return 下一个Bike的ID
	 */
	public static int nextBikeId() {
		if(BIKES.isEmpty()) {
			return 1;
		}
		return BIKES.get(BIKES.size() - 1).getId() + 1;
	}
	
}
