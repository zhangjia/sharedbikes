package tv.zhangjia.bike.dao;

import tv.zhangjia.bike.entity.UserOptions;

public interface UserOptionsDao {
	/**
	 * 添加用户设置
	 * @param us 用户设置对象
	 * @return 添加成功返回1，添加不成功返回0
	 */
	int doInsert(UserOptions us);
	/**
	 * 更新用户设置
	 * @param us 用户设置对象
	 * @return 更新成功返回1，更新失败返回0
	 */
	int doUpdate(UserOptions us);
	/**
	 * 查询用户设置
	 * @param id 要查询的用户id
	 * @param name 要查询的设置名称
	 * @return 要查询的设置所对应的值
	 */
	String queryUserSetting(int id,String name);
}