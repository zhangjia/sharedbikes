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
	 * @param username 用户名
	 * @param password 密码
	 * @return	注册的用户
	 */
	User login(String username, String password);

	/**
	 * 用户注册
	 * @param username	用户名
	 * @param password	密码
	 * @param tel	手机号
	 * @param payPassword 支付密码
	 * @return   注册是否成功，成功返回注册的ID，不成功返回0
	 */
	int register(String username, String password, String tel, String payPassword);

	/**
	 * 根据用户ID查询该用户
	 * @param userId 用户ID
	 * @return   该用户ID对应的用户
	 */
	User queryByUserId(int userId);

	/**
	 * 根据用手机号查询该用户
	 * @param tel 用户的手机号
	 * @return   该手机号所对应的用户
	 */
	User queryByUserTel(String tel);

	/**
	 * 查询所有的用户
	 * @return   所有的用户
	 */
	List<User> queryAll();

	/**
	 * 用户名是否已经存在
	 * @param username 要检测的用户名
	 * @return   存在返回true，不存在返回flase
	 */
	boolean isUserNameExist(String username);

	/**
	 * 更新用户信息
	 * @param user 该用户
	 * @return   更新成功返回1，更新失败返回0
	 */
	int doUpdate(User user);

	/**
	 * 给出建议的用户名
	 * @param username 用户注册时输入的已存在的用户名
	 * @return   系统建议的用户名
	 */
	String adviseUsername(String username);

	/**
	 * 手机号是否已存在
	 * @param tel 要判断的手机号
	 * @return   存在返回true，不存在返回false
	 */
	boolean isTelExist(String tel);

	/**
	 * 用户的支付密码是否正确
	 * @param userId
	 * @param payPassword
	 * @return   
	 */
	boolean isTruePayPassword(int userId, String payPassword);

}
