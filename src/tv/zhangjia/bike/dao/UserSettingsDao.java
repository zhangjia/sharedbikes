package tv.zhangjia.bike.dao;

import tv.zhangjia.bike.entity.UserSettings;

public interface UserSettingsDao {
	int doInsert(UserSettings us);
	boolean setActp(int id, int userId,boolean on_off);
	UserSettings queryUserSetting(int id);
}