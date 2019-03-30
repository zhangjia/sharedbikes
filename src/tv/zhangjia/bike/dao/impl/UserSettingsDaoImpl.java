package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.UserSettingsDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.UserSettings;

public class UserSettingsDaoImpl implements UserSettingsDao {
	List<UserSettings> userSettings = Database.USERSETTINGS;
	@Override
	public boolean setActp(int id, int userId) {
		
		return false;
	}

}
