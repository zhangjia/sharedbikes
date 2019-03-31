package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.UserSettingsDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.UserSettings;

public class UserSettingsDaoImpl implements UserSettingsDao {
	List<UserSettings> userSettings = Database.USERSETTINGS;
	@Override
	public boolean setActp(int id, int userId,boolean on_off) {
		UserSettings s = queryUserSetting(id);
		if(s.getUserId() == userId) {
			s.setActp(on_off);
			return true;
		}
		return false;
	}
	@Override
	public int doInsert(UserSettings us) {
		us.setId(Database.nextUserSettingsId());
		userSettings.add(us);
		return us.getId();
	}
	@Override
	public UserSettings queryUserSetting(int id) {
		for (UserSettings userSettings2 : userSettings) {
			if(userSettings2.getId() == id) {
				return userSettings2;
			}
			
		}
		return null;
	}

}
