package tv.zhangjia.bike.dao;

import java.util.List;

import tv.zhangjia.bike.entity.User;

/**
 * User接口
 * 
 * @ProjectName SharedBikes
 * @PackgeName tv.zhangjia.bike.dao
 * @ClassName UserDao
 * @author ZhangJia
 * @Version v1.0
 * @date 2019年3月25日 下午6:55:45
 */
public interface UserDao {
	
	User login(String username, String password);

	int register(String username, String password, String tel, String payPassword);

	User queryByUserId(int userId);

	List<User> queryAll();

	String queryUserName(int id);

	int queryUserId(String username);

	boolean isUserNameExist(String username);

	int doUpdate(User user);

	String adviseUsername(String username);

	boolean isTelExist(String tel);

	boolean isTruePayPassword(int userId, String payPassword);

	boolean isTrueTel(String tel, int userId);
}
