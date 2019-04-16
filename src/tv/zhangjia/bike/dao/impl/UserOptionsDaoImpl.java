package tv.zhangjia.bike.dao.impl;

import java.util.List;

import tv.zhangjia.bike.dao.UserOptionsDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.UserOptions;

public class UserOptionsDaoImpl implements UserOptionsDao {
//	List<UserSettings> userSettings = Database.USERSETTINGS;
	List<UserOptions> userOptions = Database.USEROPTIONS;

	@Override
	public int doInsert(UserOptions us) {
		us.setId(Database.nextUserOptionsId());
		userOptions.add(us);
		return us.getId();
	}



	@Override
	public String queryUserSetting(int id,String name) {
		for (UserOptions userOptions2 : userOptions) {
			if(userOptions2.getUserId() == id && userOptions2.getName().equals(name)) {
				return userOptions2.getValue();
			}
		}
		return null;
	}

	@Override
	public boolean sestValues(int userId,String name, String values) {
		for (UserOptions userOptions2 : userOptions) {
			if(userOptions2.getName().equals(name) && userOptions2.getUserId() == userId) {
				userOptions2.setValue(values);
			}
		}
		return false;
	}

	@Override
	public boolean setOptions(int userId, String name, String values) {
		for (UserOptions u2 : userOptions) {
			if(u2.getUserId()==userId && u2.getName().equals(name)) {
				u2.setValue(values);
				return true;
			}
		}
		return false;
	}
	

}
