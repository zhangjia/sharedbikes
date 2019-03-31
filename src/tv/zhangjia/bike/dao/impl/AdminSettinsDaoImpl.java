package tv.zhangjia.bike.dao.impl;

import tv.zhangjia.bike.dao.AdminSettingsDao;
import tv.zhangjia.bike.data.Database;
import tv.zhangjia.bike.entity.AdminSettings;

public class AdminSettinsDaoImpl implements AdminSettingsDao {
	AdminSettings s = Database.as;

	@Override
	public AdminSettings queryAdminSettings() {

		return s;
	}

}
