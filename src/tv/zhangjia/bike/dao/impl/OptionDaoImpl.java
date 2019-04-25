package tv.zhangjia.bike.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import tv.zhangjia.bike.dao.OptionDao;
import tv.zhangjia.bike.entity.Options;
import tv.zhangjia.bike.util.CommonDao;

public class OptionDaoImpl extends CommonDao<Options> implements OptionDao {
//	List<Options> op = Database.OPTIONS;


//	@Override
//	public List<Options> queryAlloptions() {
//		// TODO Auto-generated method stub
//		return op;
//	}
	@Override
	public List<Options> queryAlloptions() {
		String sql = "SELECT * FROM options";
		return query4BeanList(sql);
	}
	
	@Override
	public int doUpdate(String value,String name) {
		String sql = "UPDATE options SET value=? WHERE name=?";
		
		return executeUpdate(sql, value,name);
	}

	@Override
	public String queryValue(String name) {
		List<Options> op = queryAlloptions();
		for (Options options : op) {
			if(options.getName().equals(name)) {
				return options.getValue();
			}
		}
		return null;
	}

	@Override
	public Options getBeanFromResultSet(ResultSet rs) throws SQLException {
		
		return new Options(rs.getInt(1),rs.getString(2),rs.getString(3));
	}

}
