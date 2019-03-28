package tv.zhangjia.bike.dao;

import tv.zhangjia.bike.entity.User;

/**
 * User接口
 * @ProjectName	SharedBikes	  
 * @PackgeName	tv.zhangjia.bike.dao	
 * @ClassName	UserDao	
 * @author	ZhangJia
 * @Version	v1.0
 * @date	2019年3月25日 下午6:55:45
 */
public interface UserDao {
	/**
	 * 用户登录
	 * @Title	login	
	 * @param username	用户名
	 * @param password	密码
	 * @return	用户
	 */
	User login(String username, String password);
	/**
	 * 用户注册
	 * @param username 用户名
	 * @param password 用户密码
	 * @return
	 */
	int register(String username, String password);
}
