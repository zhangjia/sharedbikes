package tv.zhangjia.bike.data;

import java.util.ArrayList;
import java.util.List;

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
	
	static {
		//向数据库中默认添加一个管理员
			USERS.add(new User(1,"a","a","15628",true,10,0,0,"2019-03-25"));
		//向数据库中默认添加一个数据库
			USERS.add(new User(2,"b","b","15629",false,10,0,0,"2019-03-25"));
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
}
