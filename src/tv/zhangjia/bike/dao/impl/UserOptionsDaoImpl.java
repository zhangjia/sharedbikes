package tv.zhangjia.bike.dao.impl;

import tv.zhangjia.bike.dao.UserOptionsDao;
import tv.zhangjia.bike.entity.UserOptions;
import tv.zhangjia.bike.util.CommonDao;

public class UserOptionsDaoImpl extends CommonDao implements UserOptionsDao {
	/**
	 * 添加用户设置
	 * @param us 用户设置对象
	 * @return 添加成功返回1，添加不成功返回0
	 */
	@Override
	public int doInsert(UserOptions us) {
		String sql = "INSERT INTO user_options VALUES(seq_ups.nextval,?,?,?)";
		return executeUpdate(sql, us.getUserId(), us.getName(), us.getValue());
	}

	/**
	 * 查询用户设置
	 * @param id 要查询的用户id
	 * @param name 要查询的设置名称
	 * @return 要查询的设置所对应的值
	 */
	@Override
	public String queryUserSetting(int id, String name) {
		String sql = "SELECT * FROM user_options WHERE user_id = ? AND name=?";
		return query4Bean(sql, UserOptions.class, id, name).getValue();
	}
	
	/**
	 * 更新用户设置
	 * @param us 用户设置对象
	 * @return 更新成功返回1，更新失败返回0
	 */
	@Override
	public int doUpdate(UserOptions us) {
		String sql = "UPDATE user_options SET value=? WHERE user_id=? AND name=?";
		return executeUpdate(sql, us.getValue(), us.getUserId(), us.getName());
	}

}
