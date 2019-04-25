package tv.zhangjia.bike.dao;

import tv.zhangjia.bike.entity.UserOptions;

public interface UserOptionsDao {
	int doInsert(UserOptions us);
//	boolean setOptions(int userId,String name,String values);
//	boolean sestValues(int userId,String name, String values);
	int doUpdate(UserOptions us);
	String queryUserSetting(int id,String name);
}