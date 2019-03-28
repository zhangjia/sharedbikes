package tv.zhangjia.bike.dao.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import tv.zhangjia.bike.dao.UserDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.User;

public class UserDaoImpl implements UserDao {
	private List<User> users = Database.USERS;

	/**
	 * 重写登录方法
	 * @param username	登录的用户名
	 * @param password	登录的密码
	 * @return   用户
	 * @see tv.zhangjia.bike.dao.UserDao#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String username, String password) {
		for(User user : users) {
			//如果用户名和密码匹配，则返回该用户
			if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		// 如果用户名和密码不匹配，则返回null
		return null;
	}

	/**
	 * 重写注册方法
	 * @param username	注册的用户名
	 * @param password	注册的密码
	 * @return   
	 * @see tv.zhangjia.bike.dao.UserDao#register(java.lang.String, java.lang.String)
	 */
	@Override
	public int register(String username, String password) {
		
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return -1; //用户名已经存在
			}
		}
		String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		users.add(new User(Database.nextUserId(),username,password,"无",false,5,0,
				0, nowDate));
		return 1;
	}

}
