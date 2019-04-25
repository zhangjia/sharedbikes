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
	/**
	 * 用户登录
	 * 
	 * @Title login
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 用户
	 */
	User login(String username, String password);

	/**
	 * 用户注册
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            用户密码
	 * @return
	 */
	int register(String username, String password, String tel,String payPassword);

	/**
	 * 根据用户ID返回指定用户
	 * 
	 * @param userID
	 * @return
	 */
	User queryByUserId(int userId);

	/**
	 * 查询所有用户的信息
	 */

	List<User> queryAll();

	/**
	 * 根据用户ID返回用户名
	 * 
	 * @param id
	 * @return
	 */
	String queryUserName(int id);

	/**
	 * 根据用户名返回用户ID
	 * @param username
	 * @return
	 */
	int queryUserId(String username);


//	/**
//	 * 修改密码
//	 * @param userId
//	 * @param editPassword
//	 * @return
//	 */
//	int editPassword(int userId, String editPassword);
//
//	/**
//	 * 找回密码
//	 * @param userId
//	 * @param password
//	 * @return
//	 */
//	User retrievePassword(int userId, String password);

	/**
	 * 是否存在该用户名
	 * @param username
	 * @return
	 */
	boolean isExistUserName(String username);

	int doUpdate(User user);

	String adviseUsername(String username);
	
	boolean isTelExist(String tel);
	
	boolean isTruePayPassword(int userId, String payPassword);
	boolean isTrueTel(String tel,int userId);
}
