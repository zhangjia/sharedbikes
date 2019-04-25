package tv.zhangjia.bike.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import tv.zhangjia.bike.dao.UserOptionsDao;
import tv.zhangjia.bike.entity.UserOptions;
import tv.zhangjia.bike.util.CommonDao;

public class UserOptionsDaoImpl extends CommonDao implements UserOptionsDao {
//	List<UserSettings> userSettings = Database.USERSETTINGS;
//	List<UserOptions> userOptions = Database.USEROPTIONS;

//	@Override
//	public int doInsert(UserOptions us) {
//		us.setId(Database.nextUserOptionsId());
//		userOptions.add(us);
//		return us.getId();
//	}
	@Override
	public int doInsert(UserOptions us) {
		String sql = "INSERT INTO user_options VALUES(seq_ups.nextval,?,?,?)";
		return executeUpdate(sql, us.getUserId(),us.getName(),us.getValue());
	}


//
//	@Override
//	public String queryUserSetting(int id,String name) {
//		for (UserOptions userOptions2 : userOptions) {
//			if(userOptions2.getUserId() == id && userOptions2.getName().equals(name)) {
//				return userOptions2.getValue();
//			}
//		}
//		return null;
//	}

	@Override
	public String queryUserSetting(int id,String name) {
		String sql = "SELECT * FROM user_options WHERE user_id = ? AND name=?";
		return query4Bean(sql, id,name).getValue();
	}

//	@Override
//	public boolean sestValues(int userId,String name, String values) {
//		for (UserOptions userOptions2 : userOptions) {
//			if(userOptions2.getName().equals(name) && userOptions2.getUserId() == userId) {
//				userOptions2.setValue(values);
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public boolean setOptions(int userId, String name, String values) {
//		for (UserOptions u2 : userOptions) {
//			if(u2.getUserId()==userId && u2.getName().equals(name)) {
//				u2.setValue(values);
//				return true;
//			}
//		}
//		return false;
//	}

	

	@Override
	public UserOptions getBeanFromResultSet(ResultSet rs) throws SQLException {
		UserOptions us  = new UserOptions(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4));
		return us;
	}


	@Override
	public int doUpdate(UserOptions us) {
		String sql = "UPDATE user_options SET value=? WHERE user_id=? AND name=?";
		
		return executeUpdate(sql, us.getValue(),us.getUserId(),us.getName());
	}
		

}
